package texthandler;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileReader {
    /** the file name from which we read  info*/
    private String fileName;

    private RandomAccessFile file;

    public FileReader(String fileName){
        this.fileName = fileName;
    }

    public byte[] readBytesFromFile(long position, int size) throws IOException {
        this.file = new RandomAccessFile(fileName, "rw");
        file.seek(position);
        byte[] bytes = new byte[size];
        file.read(bytes);
        file.close();
        return bytes;
    }

    /**
     * transform byte array to string (char array), using as parameter the position relative in the
     * byte array from where the bytes are taken and the length of the objective string which is the same as
     * the number of bytes that are going to be used.
     *
     * @param data the byte array used to create the string.
     * @param position the position in the byte array from where we start taking the bytes.
     * @param length the number of characters (i.e. the number of bytes).
     * @return
     */
    public String bytesToString(byte[] data, int position, int length){
        StringBuilder sb = new StringBuilder();
        for (int i = position; i - position < length && i < data.length; i++){
            sb.append(byteToChar(data, i));
        }
        return sb.toString();
    }

    /**
     * transform a byte to char format, receive a byte array and the position of the byte that is going
     * to be transformed
     *
     * @param data byte array with the data
     * @param position the position of the byte that is going to be transformed
     * @return the transformed byte to char format
     */
    public char byteToChar(byte[] data, int position) {
        return (char) data[position];
    }

    public String readText(long position, int charLength) throws IOException {
        if (charLength == -1)
            charLength = (int)getLenght();
        byte[] array = readBytesFromFile(position, charLength);
        return bytesToString(array, 0, charLength);
    }

    /**
     * return the lenght of the file
     * @return long numbert that represent length of file in bytes.
     * @throws IOException
     */
    public long getLenght() throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        long ret = file.length();
        file.close();
        return ret;
    }
}
