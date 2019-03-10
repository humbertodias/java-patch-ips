package jips;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;

/**
 * <title>IpsPatcher class</title>
 * <h1>IpsPatcher class/Program</h1>
 * <h2>About</h2>
 * <jips><b>Version 1.0</b></jips>
 * <jips><b>Author: Rolf Stenholm <a href="mailto:rolfstenholm@yahoo.se"> rolfstenholm@yahoo.se</a> </b></jips>
 * <jips><b>Usage:</b> java IpsPatcher patch file.ips target file</jips>
 * Patches target file with ips patch format.
 * <h2>What this is:</h2>
 * An ips file patcher for use on any machine.
 * <h2>What it does</h2>
 * Patches images of files with an ips files. Often used to translate programs to a new language.
 * <h2>License</h2>
 * <pre>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * <a href="http://www.gnu.org">See www.gnu.org</a>
 * </pre>
 * <h2>Description</h2>
 * This is description of the ips file format, source of this descriton comes from
 * <a href="http://zerosoft.zophar.net/ips.htm">zerosoft.zophar.net</a>.
 * <br>
 * <pre>
 *
 * File format:
 * Header     5 bytes = "PATCH"
 *
 * Records in ips file
 *
 * Eof marker 3 bytes = "EOF"
 *
 * Record (normal)
 * Offset   3 bytes
 * Size     2 bytes
 * Data     Size number of bytes
 *
 * Record RLE Compressed
 * Offset   3 bytes
 * Size     2 bytes         = 0 (identifies RLE)
 * RLE_Size 2 bytes
 * Value    1 byte         value to repeat RLE_Size times
 *
 * </pre>
 * All values are stored in big endnian. Notice that this format has an EOF symbol and size field collision,
 * this class does not try to 'fix' the problem.
 */
public class IpsPatcher {

    private RandomAccessFile input;
    private RandomAccessFile output;

    public IpsPatcher(String pathFileName, String targetFileName, boolean backup) throws IOException {
        this(new File(pathFileName), new File(targetFileName), backup);
    }

    public IpsPatcher(File pathFile, File targetFile, boolean backup) throws IOException {
        input = new RandomAccessFile(pathFile, "r");
        if (backup) {
            backup(targetFile);
        }
        output = new RandomAccessFile(targetFile, "rw");
    }

    private void backup(File targetFile) throws IOException {
        File bkp = new File(targetFile.getAbsolutePath() + ".bkp");
        Files.copy(targetFile.toPath(), bkp.toPath());
    }

    /* patch file */
    public void patch() throws IOException {

        byte lookaHead[] = new byte[3];
        byte r_size[] = new byte[2];

        readHead();

        for (input.read(lookaHead); !eofMarker(lookaHead); input.read(lookaHead)) {
            output.seek(getUnsignedSmallInt(lookaHead[0], lookaHead[1], lookaHead[2]));
            input.read(r_size);
            int i_size = getUnsignedShort(r_size[0], r_size[1]);
            if (i_size == 0) {
                writeRLERecord();
            } else {
                byte data[] = new byte[i_size];
                input.read(data);
                output.write(data);
            }

        }
    }

    /* Check head */
    private void readHead() throws IOException {
        byte head[] = new byte[5];
        String headTemplate = "PATCH";
        input.read(head);
        if (headTemplate.equals(new String(head))) {
        } else {
            throw new RuntimeException("Patch file not in ips format! ");
        }
    }

    /* check for Eof marker */
    private boolean eofMarker(byte... chs) {
        return "EOF".equals(new String(chs));
    }

    /**
     * Write RLERecord
     */
    private void writeRLERecord() throws IOException {
        byte r_RLEsize[] = new byte[2];
        input.read(r_RLEsize);
        int stride = getUnsignedShort(r_RLEsize[0], r_RLEsize[1]);
        int val = getUnsignedByte(input.read());

        for (int i = 0; i < stride; i++) {
            output.write(val);
        }
    }

    /* convert from unsigned byte */
    private static int getUnsignedByte(int b) {
        return b & 0xFF;
    }

    /* convert from unsigned 2 byte */
    private static int getUnsignedShort(int f, int s) {
        return getUnsignedByte(f) << 8 | getUnsignedByte(s);
    }

    /* convert from unsigned 3 byte */
    private static int getUnsignedSmallInt(int f, int s, int t) {
        return getUnsignedByte(f) << 16 | getUnsignedShort(s, t);
    }

    public static void main(String... args) throws Exception {

        if (args.length == 2) {
            new IpsPatcher(args[0], args[1], true).patch();
        } else {
            System.err.println("Usage java IpsPatcher <patch file> <target file>");
        }

    }

}