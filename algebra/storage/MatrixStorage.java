package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

import matrix.Matrix;

/**
 * ����ࣨ�������󣩰���������({@link Matrix})�;�������װ��һ�𣬲�������һϵ�г�Ա�����ṩ�˶Գ�Ա���ݵķ��ʡ��޸ķ�ʽ���Լ��Դ����ļ��Ķ�д��ʽ��
 * <p>
 * ����౻�������({@link MatrixStorage})������
 */
public class MatrixStorage implements Serializable {
    private static final long serialVersionUID = -4177037293939973239L;
    private String matrixName;
    private Matrix mat;

    static String ENDNAME;

    /**
     * ���췽�������ṩ����������;�������װΪһ�������������
     * 
     * @param m ��������
     * @param s ������
     */
    public MatrixStorage(Matrix m, String s) {
        mat = m;
        matrixName = s;
    }

    /**
     * �����̬���������Ը������ļ�����·������һ�������������
     * <p>
     * �ļ����ǰ�����չ���ġ�
     * 
     * @param fileName �����ϵ��ļ���
     * @param cwd      ·��
     * @return һ�������������
     * @throws FileNotFoundException ���Ҳ���ָ�����ļ�ʱ
     */
    public static MatrixStorage readFromFile(String fileName, String cwd) throws FileNotFoundException {
        File inFile = null;
        inFile = new File(cwd + "\\" + fileName);
        Scanner sc;
        int pos = fileName.lastIndexOf('.');
        String matrixName = fileName.substring(0, pos);
        String s = "";
        sc = new Scanner(inFile);
        try {
            int r = sc.nextInt();
            int c = sc.nextInt();
            while (sc.hasNextLine())
                s += sc.nextLine() + " ";
            sc.close();
            return new MatrixStorage(Matrix.matrixFromString(r, c, s), matrixName);
        } catch (RuntimeException e) {
            sc.close();
            return null;
        }
    }

    /**
     * ������������ָ��·����
     * 
     * @param cwd ָ��·��
     * @return �Ƿ�ɹ�
     * @throws IOException �����ɹ�
     */
    public boolean saveToFile(String cwd) throws IOException {
        PrintWriter pw;
        File outFile = new File(cwd + "\\" + matrixName + MatrixManager.ENDNAME);
        if (!outFile.exists())
            outFile.createNewFile();
        if (!outFile.canWrite()) {
            // System.out.println("Cannot write file " + matrixName +MatrixManager.ENDNAME);
            return false;
        } else {
            pw = new PrintWriter(outFile);
            pw.println(mat.getRows() + " " + mat.getCols());
            pw.println(getMat());
            pw.close();
            return true;
        }
    }

    /**
     * ��ȡ������(��������Ϊ�ļ�����չ��)��
     * 
     * @return ������
     */
    public String getName() {
        return matrixName;
    }

    /**
     * ����{@code this}����������({@link Matrix})����
     * 
     * @return һ��{@link Matrix}����
     */
    public Matrix getMat() {
        return mat;
    }

    /**
     * �Ӹ�����·��ɾ�������ļ���
     * 
     * @param cwd ·��
     * @return �Ƿ�ɹ�
     */
    public boolean deleteFromFile(String cwd) {
        boolean ret = true;
        File outFile = new File(cwd + "\\" + matrixName + MatrixManager.ENDNAME);
        if (outFile.exists()) {
            if (!outFile.delete())
                ret = false;
        }
        return ret;
    }
}