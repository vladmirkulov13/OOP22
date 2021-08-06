package functions;

import java.io.*;

public class FunctionClassLoader extends ClassLoader {

    public FunctionClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class<?> findClass(String classFilePath) throws ClassNotFoundException {
        String className = classFilePath.substring(classFilePath.lastIndexOf('/')+1, classFilePath.indexOf(".class"));
        try {
            byte[] byteCode = fetchClassFromFile(classFilePath);
            return defineClass(null, byteCode, 0, byteCode.length);
        } catch (IOException e) {
            return super.findClass(className);
        }
    }

    public byte[] fetchClassFromFile(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        byte[] bytes = new byte[(int) new File(path).length()];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) throw new IOException("Could not completely read file " + path);
        is.close();
        return bytes;
    }
}
