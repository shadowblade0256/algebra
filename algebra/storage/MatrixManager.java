package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import matrix.Matrix;
import util.Container;

/**
 * ����ࣨ���������{@link MatrixStorage}������ݼ��ϵĹ������࣬�ں�{@code ArrayList<MatrixStorage>}���͵��ֶ��Դ洢{@link MatrixStorage}������ݼ��ϣ���������һϵ�г�Ա�����ṩ�˶Գ�Ա���ݵķ��ʡ��޸ķ�ʽ��
 * <p>
 * һ��������һ�����ݴ洢·���ҹ�����������·�������е���.matdatΪ��չ���ľ��������ļ���
 * <p>
 * ���������{@link Matrix}��{@link MatrixStorage}�����������ࡣ
 * 
 * @see Matrix ������
 * @see MatrixStorage ��װ�˾������ľ�����
 */
public class MatrixManager {
    public static String ENDNAME = ".matdat";
    private ArrayList<MatrixStorage> rec;
    private String cwd;

    /**
     * ����MatrixStorage�࣬�ӹ�ָ���Ĺ���·������ȡ��·����ȫ���ľ��������ļ���������ȡ�쳣��һ���ļ���д��������
     * 
     * @param cwd ����·��������ʹ�����·����
     * @param s   ���Դ洢��ȡ�쳣���ļ��������������
     */
    public MatrixManager(String cwd, Container<String[]> s) {
        this.cwd = cwd;
        MatrixStorage.ENDNAME = ENDNAME;
        s.data = reloadAll();
    }

    /**
     * ����MatrixStorage�࣬�ӹ�ָ���Ĺ���·������ȡ��·����ȫ���ľ��������ļ���������ȡ�쳣��һ���ļ���д��������
     * 
     * @param cwd     ����·��������ʹ�����·����
     * @param s       ���Դ洢��ȡ�쳣���ļ��������������
     * @param endname ָ�����������ļ�����չ�����������
     */
    public MatrixManager(String cwd, Container<String[]> s, String endname) {
        ENDNAME = endname;
        this.cwd = cwd;
        MatrixStorage.ENDNAME = ENDNAME;
        s.data = reloadAll();
    }

    /**
     * ���洢��ȫ��{@link MatrixStorage}����ľ�����{@code matrixName}�б��ء�
     * 
     * @return �������б�
     */
    public String[] getNameList() {
        int len = getSize();
        String ret[] = new String[getSize()];
        for (int i = 0; i <= len - 1; i++)
            ret[i] = rec.get(i).getName();
        return ret;
    }

    /**
     * ���ļ��洢Ŀ¼���¼������о����ļ����˲����ᶪʧ�ڴ���û�б��浽�����ϵľ������ݣ����ض�ȡʧ�ܵľ������б�
     * 
     * @return ��ȡʧ�ܵľ������б�
     */
    public String[] reloadAll() {
        rec = new ArrayList<MatrixStorage>();
        File currentLocation = new File(cwd);
        if (!currentLocation.isDirectory())
            currentLocation.mkdir();
        String[] fileNameList = currentLocation.list();
        ArrayList<String> failList = new ArrayList<String>();
        for (String x : fileNameList)
            if (x.endsWith(ENDNAME))
                try {
                    MatrixStorage gotten = MatrixStorage.readFromFile(x, cwd);
                    if (gotten != null)
                        rec.add(gotten);
                    else
                        failList.add(x);
                } catch (FileNotFoundException e) {
                    failList.add(x);
                }
        return failList.toArray(new String[failList.size()]);
    }

    /**
     * ���Խ�����Ϊ{@code index}�ľ��󱣴浽���̣������Ƿ�ɹ���
     * 
     * @param index ����
     * @return �Ƿ�ɹ�
     * @throws IOException �����ɹ�
     */
    public boolean saveToFile(int index) throws IOException {
        return rec.get(index).saveToFile(cwd);
    }

    /**
     * ָ��һ��{@link MatrixStorage}���������̣������Ƿ�ɹ���
     * <p>
     * �˲������Ὣ{@code e}���������ֶ�
     * 
     * @param e Ҫ����ľ���
     * @return �Ƿ�ɹ�
     * @throws IOException �����ɹ�
     */
    public boolean saveToFile(MatrixStorage e) throws IOException {
        return e.saveToFile(cwd);
    }

    /**
     * �ӱ�׼����������ָ����С�ľ��󣬲��Ը����ľ��������浽�����ֶΡ�
     * 
     * @param r          ����
     * @param c          ����
     * @param matrixName ������
     */
    public void createNewFromInput(int r, int c, String matrixName) {
        rec.add(new MatrixStorage(Matrix.matrixFromInput(r, c), matrixName));
    }

    /**
     * �������ֶεľ���ȫ��������̣������Ƿ�ɹ���
     * 
     * @return �Ƿ�ɹ���
     * @throws IOException �����ɹ�
     */
    public boolean saveAll() throws IOException {
        boolean ret = true;
        for (MatrixStorage e : rec) {
            if (!saveToFile(e)) {
                ret = false;
                // System.out.println("Failed to save " + e.getName());
            }
        }
        return ret;
    }

    /**
     * �Ӵ������¼�������Ϊ{@code index}��ͬ������
     * 
     * @param index ����
     * @throws FileNotFoundException �Ҳ����ļ�
     */
    public void reload(int index) throws FileNotFoundException {
        String matrixName = rec.get(index).getName();
        MatrixStorage.readFromFile(matrixName + ENDNAME, cwd);
    }

    /**
     * ��������Ϊ{@code index}�ľ���({@link Matrix}����)
     * 
     * @param index ����
     * @return һ�����󣬲���������(��{@link Matrix}����)
     */
    public Matrix getMatrix(int index) {
        return rec.get(index).getMat();
    }

    /**
     * �������ֶε�ĩβ����һ���������������¾���({@link Matrix}��)
     * 
     * @param mat ����{@link Matrix}��
     * @param s   ������
     */
    public void add(Matrix mat, String s) {
        add(new MatrixStorage(mat, s));
    }

    /**
     * �������ֶε�ĩβ����һ���¾���({@link MatrixStorage}��)
     * 
     * @param mat ����{@link MatrixStorage}��
     */
    public void add(MatrixStorage matstorage) {
        rec.add(matstorage);
    }

    /**
     * ���������ֶ���{@link MatrixStorage}�б�
     * 
     * @return �����б�
     */
    public MatrixStorage[] getList() {
        int len = rec.size();
        MatrixStorage[] ret = new MatrixStorage[len];
        rec.toArray(ret);
        return ret;
    }

    /**
     * �����Ѿ��洢�ľ��������
     * 
     * @return �Ѿ��洢�ľ��������
     */
    public int getSize() {
        return rec.size();
    }

    /**
     * �������ֶ�ɾ������Ϊ{@code index}�ľ���
     * 
     * @param index Ҫɾ���ľ��������
     * @return �Ƿ�ɹ�
     */
    public boolean delete(int index) {
        File of = new File(cwd + "\\" + rec.get(index).getName() + ENDNAME);
        rec.remove(index);
        if (of.exists())
            return of.delete();
        return true;
    }

    /**
     * ��������ֶ����Ƿ��д����ϲ��������Ӧ�ļ��ľ���
     * <p>
     * �����������Ƿ���ͬ
     * 
     * @return �����ϲ��������ļ��ľ�����������б�
     */
    public int[] checkNotSaved() {
        String[] currentLocation = new File(cwd).list();
        MatrixStorage[] tmp = rec.toArray(new MatrixStorage[rec.size()]);
        int len = tmp.length;
        for (String x : currentLocation) {
            if (x.endsWith(ENDNAME))
                for (int i = 0; i <= len - 1; i++)
                    if (tmp[i] != null && x.equals(tmp[i].getName() + ENDNAME)) {
                        tmp[i] = null;
                        break;
                    }
        }
        int[] ret = new int[len];
        int pos = 0;
        for (int i = 0; i <= len - 1; i++)
            if (tmp[i] != null) {
                ret[pos] = i;
                pos++;
            }
        return ret;
    }

    /**
     * ��������Ϊ{@code index}�ľ���({@link MatrixStogare}����)
     * 
     * @param index ����
     * @return һ�����󣬰���������(��{@link Matrix}����)
     */
    public MatrixStorage get(int index) {
        return rec.get(index);
    }

    /**
     * �Ը����ľ����滻�����ֶ���ָ������{@code index}������
     * 
     * @param index ����
     * @param mat   �滻�ɵľ��󣬰���������(��{@link MatrixStorage}����)
     * @throws IOException
     */
    public void changeMatrix(int index, MatrixStorage mat) throws IOException {
        rec.get(index).deleteFromFile(cwd);
        rec.set(index, mat);
        saveToFile(index);
    }

    /**
     * �Ӵ��̺������ֶ�ɾ������Ϊ{@code index}�ľ���
     * 
     * @param index ����
     */
    public void deleteFromFile(int index) {
        rec.get(index).deleteFromFile(cwd);
        rec.remove(index);
    }
}