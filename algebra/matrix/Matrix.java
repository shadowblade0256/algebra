package matrix;

import java.io.Serializable;
import java.util.Scanner;

import exception.MatrixIllegalOperationException;
import exception.MatrixIndexOutException;
import exception.MatrixNotSquareException;
import exception.MatrixWrongSizeException;
import number.Fraction;
import util.GCD;

/**
 * �����ʹ�ö�ά������˳��洢���󣬲�������һϵ�еĳ�Ա���������ɾ���ͶԾ���������㡣
 * <p>
 * �ṩ�˾�̬����{@link #matrixFromInput(int, int)}��{@link #matrixFromString(int, int, String)}�ӱ�׼��������������ַ����н�������
 * ��Щ�����ķ���ֵ����Ϊ{@link Fraction}����{@code algebra.number.Fraction}�е��࣬�����洢��������
 * <p>
 * {@code Matrix}��{@code algebra}���е�������������
 * 
 * @see Fraction
 * @author Sochiji
 */
public final class Matrix implements Cloneable, Serializable {

    private static final long serialVersionUID = 4795541498067910217L;

    /**
     * ������{@code size}Ϊ��С�ĵ�λ����
     * 
     * @param size ��λ����Ĵ�С
     * @return ��λ����
     * @see {@link #getE(int,Fraction)}
     */
    public static Matrix getE(int size) {
        return getE(size, new Fraction(1));
    }

    /**
     * ���ɵ�λ�����{@code lambda}������
     * 
     * @param size   ��λ����Ĵ�С
     * @param lambda ����ֵ����ڵ�λ����ı���
     * @return ��λ�����{@code lambda}��
     * @see {@link #getE(int)}
     */
    public static Matrix getE(int size, Fraction lambda) {
        Fraction[][] ret = new Fraction[size][size];
        for (int i = 0; i <= size - 1; i++)
            for (int j = 0; j <= size - 1; j++)
                if (i == j)
                    ret[i][j] = lambda;
                else
                    ret[i][j] = new Fraction(0);
        return new Matrix(ret);
    }

    /**
     * ��������Ԫ��Ϊ��Ԫ����
     * 
     * @param size Ҫ���ɵľ���Ĵ�С
     * @return һ��ֻ����Ԫ�ķ���
     * @see {@link #getZero(int, int)}
     */
    public static Matrix getZero(int size) {
        return getZero(size, size);
    }

    /**
     * ��������Ԫ��Ϊ��Ԫ�ľ���
     * 
     * @param rows Ҫ���ɵľ��������
     * @param cols Ҫ���ɵľ��������
     * @return һ��ֻ����Ԫ�ľ���
     * @see #getZero(int)
     */
    public static Matrix getZero(int rows, int cols) {
        Fraction[][] ret = new Fraction[rows][cols];
        Fraction z = new Fraction(0);
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                ret[i][j] = z;
        return new Matrix(ret);

    }

    /**
     * �ӱ�׼����������һ������������������{@link Fraction}������ɡ�
     * ��������ÿ��{@linkplain Fraction}��ֵ���м��ÿո������
     * 
     * @param r Ҫ��ȡ�ľ��������
     * @param c Ҫ��ȡ�ľ��������
     * @return ����ľ���
     * @throws MatrixWrongSizeException ��{@code r==0}��{@code c==0}
     */
    public static Matrix matrixFromInput(int r, int c) throws MatrixWrongSizeException {
        if (r == 0 || c == 0)
            throw new MatrixWrongSizeException();
        System.out.println("Input a Matrix for " + r + " rows, " + c + " column s: ");
        Fraction[][] num = new Fraction[r][c];
        for (int i = 0; i <= r - 1; i++)
            for (int j = 0; j <= c - 1; j++)
                num[i][j] = Fraction.fractionFromInput();
        return new Matrix(num);
    }

    /**
     * ���ַ���{@code s}������{@code r}��{@code c}�еľ���
     * 
     * @param r Ҫ�����ľ��������
     * @param c Ҫ�����ľ��������
     * @param s ���������ַ���
     * @return ���������ַ���
     * @throws NumberFormatException            ���ָ�ʽ����
     * @throws java.util.NoSuchElementException �ַ����е���������������
     */

    public static Matrix matrixFromString(int r, int c, String s) {
        Scanner sc = new Scanner(s);
        Fraction[][] num = new Fraction[r][c];
        for (int i = 0; i <= r - 1; i++)
            for (int j = 0; j <= c - 1; j++)
                num[i][j] = Fraction.valueOf(sc.next());
        sc.close();
        return new Matrix(num);
    }

    private int rows = -1;

    private int cols = -1;

    private Fraction[][] num;

    /**
     * ��һ��{@link Fraction}��Ķ�Ԫ��������һ������
     * 
     * @param fracNum ���������
     * @see {@link #Matrix(long[][])}
     * @see {@link Fraction#Fraction(long)}
     */
    public Matrix(Fraction[][] fracNum) {
        rows = fracNum.length;
        cols = fracNum[0].length;
        if (rows == 0 || cols == 0)
            throw new MatrixWrongSizeException();
        num = fracNum;
    }

    /**
     * ��һ��{@code long}���͵Ķ�ά��������һ������
     * 
     * @param intNum ���������
     * @see {@link #Matrix(Fraction[][])}
     */
    public Matrix(long[][] intNum) {
        rows = intNum.length;
        cols = intNum[0].length;
        if (rows == 0 || cols == 0)
            throw new MatrixWrongSizeException();
        num = new Fraction[rows][cols];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                num[i][j] = new Fraction(intNum[i][j]);
    }

    /**
     * ��{@code this}��{@code o }���
     * 
     * @param o Ҫ��{@code this}��ӵľ���
     * @return ��Ӻ�Ľ��
     * @throws MatrixWrongSizeException ��������ͬ��
     */
    public Matrix add(Matrix o) throws MatrixWrongSizeException {
        if (rows == o.rows && cols == o.cols) {
            Fraction[][] oFrac = o.getValue();
            Fraction[][] retFrac = new Fraction[rows][cols];
            for (int i = 0; i <= rows - 1; i++)
                for (int j = 0; j <= cols - 1; j++)
                    retFrac[i][j] = num[i][j].add(oFrac[i][j]);
            return new Matrix(retFrac);
        } else {
            throw new MatrixWrongSizeException();
        }
    }

    /**
     * �ѵ�{@code from + 1}�е�{@code rate}���ӵ���{@code to + 1}����
     * 
     * @param from
     * @param to
     * @param rate
     * @throws MatrixIndexOutException
     * @see {@link #addByRow(int, int, Fraction)}
     */

    protected void addByColumn(int from, int to, Fraction rate) throws MatrixIndexOutException {
        if (from > cols - 1 || cols < 0 || to > cols - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= rows - 1; i++)
            num[i][to] = num[i][to].add(num[i][from].mul(rate));
    }

    /**
     * * �ѵ�{@code from + 1}�е�{@code rate}���ӵ���{@code to + 1}����
     * 
     * @param from
     * @param to
     * @param rate
     * @throws MatrixIndexOutException
     * @see {@link #addByColumn(int, int, Fraction)}
     */
    protected void addByRow(int from, int to, Fraction rate) throws MatrixIndexOutException {
        if (from > rows - 1 || from < 0 || to > rows - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= cols - 1; i++)
            num[to][i] = num[to][i].add(num[from][i].mul(rate));
    }

    /**
     * ��{@code o}�ϲ���{@code this}������
     * 
     * @param o Ҫ������ϲ��ľ���
     * @return һ�����󣬺ϲ���Ľ��
     * @see #rightCombine(Matrix)
     */

    public Matrix belowCombine(Matrix o) {
        if (cols != o.cols)
            throw new MatrixWrongSizeException();
        Fraction[][] cl = o.getValue();
        Fraction[][] ret = new Fraction[rows + o.rows][cols];
        for (int j = 0; j <= cols - 1; j++) {
            for (int i = 0; i <= rows - 1; i++)
                ret[i][j] = num[i][j];
            for (int i = 0; i <= o.rows - 1; i++)
                ret[i + rows][j] = cl[i][j];
        }
        return new Matrix(ret);
    }

    /**
     * ���{@code this}
     */
    protected void clear() {
        rows = -1;
        cols = -1;
        num = null;
    }

    @Override
    protected Matrix clone() {// Deep Clone Needed
        Fraction[][] ret = new Fraction[rows][cols];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                ret[i][j] = num[i][j];
        return new Matrix(ret);
    }

    /**
     * ���㷽�������ʽ
     * 
     * @return һ��{@code Fraction}���󣬱�ʾ��������ʽ��ֵ��
     * @throws MatrixNotSquareException ��{@code this}��{@code rows}��{@code cols}����ȡ�
     */
    public Fraction detCal() throws MatrixNotSquareException {
        if (rows != cols)
            throw new MatrixNotSquareException();
        if (rows == 1)
            return num[0][0];
        if (rows == 2)
            return num[0][0].mul(num[1][1]).sub(num[0][1].mul(num[1][0]));
        int posR = 0, posC = 0;
        Matrix tmp = clone();
        Fraction[][] cl = tmp.getValue();
        do {
            posR = posC;// Square Only
            if (cl[posR][posC].getA() == 0) {// ���Խ�������Ԫ��Ϊ0
                do {
                    posR++;
                } while (posR <= cl.length - 1 && cl[posR][posC].getA() == 0);// Ѱ�Ҵ����з�0Ԫ��
                if (posR > cl.length - 1)
                    return new Fraction(0);// �Ҳ�����0Ԫ�� �����ȫΪ0
                else
                    tmp.addByRow(posR, posC, new Fraction(1));// ����0Ԫ���мӵ�0Ԫ�ضԽ���������
            }
            posR = posC;// Square Only
            do {
                posR++;
                Fraction rate = cl[posR][posC].div(cl[posC][posC]);
                tmp.addByRow(posC, posR, rate.getMinus());
                // System.out.println();
                // new Matrix(cl).print();// Debug
            } while (posR < rows - 1);
            posC++;
        } while (posC < cols - 1);
        posR = 0;
        posC = 0;
        Fraction ret = new Fraction(1);
        for (int i = 0; i <= rows - 1; i++)
            ret = ret.mul(cl[i][i]);
        return ret;
    }

    /**
     * ����{@code cols}��ֵ�����˾����������
     * 
     * @return {@code cols}
     */
    public int getCols() {
        return cols;
    }

    public Matrix getColumnVector(int... index) {
        Fraction[][] ret = new Fraction[rows][index.length];
        int count = 0;
        for (int j : index) {
            j--;
            if (j < 0 || j >= cols)
                throw new MatrixIndexOutException();
            for (int i = 0; i <= rows - 1; i++)
                ret[i][count] = num[i][j];
            count++;
        }
        return new Matrix(ret);
    }

    /**
     * ���������
     * 
     * @return {@code this}�������
     * @throws MatrixIllegalOperationException �����󲻿��档
     */
    public Matrix getInverse() {
        if (!isSquare() || detCal().getA() == 0)
            throw new MatrixIllegalOperationException("Not Inversable.");
        int[] index = new int[cols];
        for (int i = 0; i <= cols - 1; i++)
            index[i] = i + cols + 1;
        return rightCombine(getE(rows)).getRowSimplified().getColumnVector(index);
    }

    /**
     * �����н����ξ���
     * 
     * @return {@code this}���н�����
     */
    public Matrix getRowLadder() {
        int posR;
        int posC;
        int nonZeroRow = 0;
        int nonZeroCol = 0;
        Matrix ret = clone();
        Fraction[][] cl = ret.getValue();
        do {
            posR = nonZeroRow;
            posC = nonZeroCol;
            while (posR <= rows - 1 && cl[posR][posC].getA() == 0)// Ѱ�����е��׸�����Ԫ��
                posR++;
            if (posR <= rows - 1) {
                if (posR != nonZeroRow)// �����еĵ�һԪ��Ϊ0
                    ret.addByRow(posR, nonZeroRow, new Fraction(1));
                posR = nonZeroRow;
                do {
                    posR++;
                    if (posR == rows)
                        break;
                    Fraction rate = cl[posR][posC].div(cl[nonZeroRow][nonZeroCol]);
                    ret.addByRow(nonZeroRow, posR, rate.getMinus());
                    // System.out.println();
                    // new Matrix(cl).print();// Debug
                } while (posR < rows - 1);
                nonZeroRow++;
            }
            nonZeroCol++;
        } while (nonZeroCol <= cols - 1);
        return ret;
    }

    /**
     * ����{@code rows}��ֵ�����˾����������
     * 
     * @return {@code rows}
     */
    public int getRows() {
        return rows;
    }

    /**
     * ����������ξ���
     * 
     * @return {@code this}���������
     */
    public Matrix getRowSimplified() {
        Matrix ret;
        ret = getRowLadder();
        Fraction[][] cl = ret.getValue();
        int posR = rows - 1;
        do {
            int posC = cols;
            for (int i = 0; i <= cols - 1; i++)
                if (cl[posR][i].getA() != 0) {
                    posC = i;
                    break;
                }
            if (posC != cols) {
                Fraction rate = cl[posR][posC].getInverse();
                ret.mulByRow(posR, rate);
                for (int i = posR - 1; i >= 0; i--) {
                    rate = cl[i][posC].div(cl[posR][posC]);
                    ret.addByRow(posR, i, rate.getMinus());
                    // System.out.println(ret);// Debug
                }
            }
            posR--;
        } while (posR >= 0);
        return ret;
    }

    /**
     * ��ȡ�к�Ϊindex������������ɵľ���
     * 
     * @param index ��Ϊ{@code int}��{@code int[]}���͡�
     * @return ����������ɵľ���
     */
    public Matrix getRowVector(int... index) {
        Fraction[][] ret = new Fraction[index.length][cols];
        int count = 0;
        for (int i : index) {
            i--;
            if (i < 0 || i >= rows)
                throw new MatrixIndexOutException();
            for (int j = 0; j <= cols - 1; j++)
                ret[count][j] = num[i][j];
            count++;
        }
        return new Matrix(ret);
    }

    /**
     * ��ȡ���ݲ��֡�
     * 
     * @return �洢���ݵ�{@code Fraction[][]}
     */
    Fraction[][] getValue() {
        return num;
    }

    /**
     * �ж��Ƿ�Ϊ�н����Ρ�
     * 
     * @return �Ƿ�Ϊ�н�����
     */
    boolean isRowLadder() {
        if (rows == 1 || cols == 1)
            return true;
        int max = -2;
        for (int i = 0; i <= rows - 1; i++) {
            int c = -1;
            for (int j = 0; j <= cols - 1; j++) {
                if (num[i][j].getA() != 0) {
                    c = j;
                    break;
                }
                c = cols;
            }
            if (c > max)
                max = c;
            else if (c != cols)
                return false;
        }
        return true;
    }

    /**
     * �ж��Ƿ�Ϊ����
     * 
     * @return �Ƿ�Ϊ����
     */
    public boolean isSquare() {
        return rows == cols;
    }

    /**
     * �ҳ�{@code o}
     * 
     * @param o Ҫ�ҳ˵ľ���
     * @return �������þ���
     * @throws MatrixWrongSizeException ��{@code this.cols != ��.rows}
     */
    public Matrix matMul(Matrix o) throws MatrixWrongSizeException {
        if (cols != o.rows)
            throw new MatrixWrongSizeException();
        Fraction[][] on = o.getValue();
        Fraction[][] retn = new Fraction[rows][o.cols];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= o.cols - 1; j++) {
                retn[i][j] = new Fraction(0, 1);
                for (int k = 0; k <= cols - 1; k++) {
                    retn[i][j] = retn[i][j].add(num[i][k].mul(on[k][j]));
                }
            }
        return new Matrix(retn);
    }

    /**
     * ����{@code this}��{@code a}{@code b}������
     * 
     * @param a Ҫ�������к�
     * @param b Ҫ�������к�
     * @return ������
     */
    public Matrix minorDet(int a, int b) {
        Fraction[][] retn = new Fraction[rows - 1][cols - 1];
        int rowsx = 0;
        for (int i = 0; i <= rows - 1; i++) {
            if (i == a - 1)
                continue;
            int colsx = 0;
            for (int j = 0; j <= cols - 1; j++) {
                if (j == b - 1)
                    continue;
                retn[rowsx][colsx] = num[i][j];
                colsx++;
            }
            rowsx++;
        }
        return new Matrix(retn);
    }

    /**
     * ��ĳһ���Գ�{@code rate}��
     * 
     * @param to   Ҫ�Գ˵��к�
     * @param rate ����
     * @throws MatrixIndexOutException ���ṩ��{@code to}�Ƿ�
     */
    public void mulByColumn(int to, Fraction rate) throws MatrixIndexOutException {
        if (to > rows - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= rows - 1; i++)
            num[i][to] = num[i][to].mul(rate);
    }

    /**
     * ��ĳһ���Գ�{@code rate}��
     * 
     * @param to   Ҫ�Գ˵��к�
     * @param rate ����
     * @throws MatrixIndexOutException ���ṩ��{@code to}�Ƿ�
     */

    public void mulByRow(int to, Fraction rate) throws MatrixIndexOutException {
        if (to > rows - 1 || to < 0)
            throw new MatrixIndexOutException();
        for (int i = 0; i <= cols - 1; i++)
            num[to][i] = num[to][i].mul(rate);
    }

    /**
     * ���������˾���
     * 
     * @param o Ҫ��˵�������
     * @return ���˵Ľ��
     */
    public Matrix numMul(Fraction o) {
        Fraction[][] retn = new Fraction[rows][cols];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                retn[i][j] = num[i][j].mul(o);
        return new Matrix(retn);
    }

    /**
     * ��������
     * 
     * @param n ���ݵĴ���
     * @return {@code this}��{@code n}����
     */
    public Matrix power(int n) {// Minus parameter needed
        if (!isSquare())
            throw new MatrixNotSquareException();
        Matrix ret = this;
        if (n == 1)
            return this;
        if (n == 0)
            return getE(rows);
        for (int i = 1; i <= n; i++)
            ret = ret.matMul(this);// Improve Needed
        return ret;
    }

    /**
     * ���׼�������ӡ���������
     */
    public void print() {
        for (int i = 0; i <= rows - 1; i++) {
            for (int j = 0; j <= cols - 1; j++) {
                System.out.print(num[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * ���׼�������ӡ��������ݵ�һ���ڡ�
     */
    public void printLinear() {
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++) {
                System.out.print(num[i][j]);
                System.out.print(" ");
            }
    }

    /**
     * �������ȡ�
     * 
     * @return {@code this}����
     */
    public long rank() {
        Matrix ret;
        ret = getRowLadder();
        Fraction[][] cl = ret.getValue();
        int posR = rows - 1;
        int emptyRows = 0;
        do {
            boolean isEmpty = true;
            for (int i = 0; i <= cols - 1; i++)
                if (cl[posR][i].getA() != 0) {
                    isEmpty = false;
                    break;
                }
            if (isEmpty)
                emptyRows++;
            posR--;
        } while (posR > 0);
        return Math.min(cols, rows - emptyRows);
    }

    /**
     * ��{@code o}�ϲ���{@code this}���Ҳ�
     * 
     * @param o Ҫ�ϲ��ľ���
     * @return �ϲ���Ľ��
     * @throws MatrixWrongSizeException ��{@code this.rows != o.rows}
     */
    public Matrix rightCombine(Matrix o) {
        if (rows != o.rows)
            throw new MatrixWrongSizeException();
        Fraction[][] cl = o.getValue();
        Fraction[][] ret = new Fraction[rows][cols + o.cols];
        for (int i = 0; i <= rows - 1; i++) {
            for (int j = 0; j <= cols - 1; j++)
                ret[i][j] = num[i][j];
            for (int j = 0; j <= o.cols - 1; j++)
                ret[i][j + rows] = cl[i][j];
        }
        return new Matrix(ret);
    }

    /**
     * ����{@code this}�Ĵ�С����{@code this}�ǿ��������մ˾���
     * 
     * @param r ����
     * @param c ����
     * @see #clear()
     */
    public void setSize(int r, int c) throws MatrixIllegalOperationException {
        if (num != null)
            clear();
        rows = r;
        cols = c;
    }

    /**
     * �ó����б任��ȥ������Ԫ�صķ�ĸ��
     * 
     * @return �����ľ���
     */
    public Matrix simplifyByRow() {
        Matrix ret = clone();
        Fraction[][] cl = ret.getValue();
        int r = ret.rows;
        int c = ret.cols;
        for (int i = 0; i <= r - 1; i++) {
            long maxf = 1;
            for (int j = 0; j <= c - 1; j++)
                maxf = GCD.minCommonMultiple(maxf, cl[i][j].getB());
            Fraction f = new Fraction(maxf);
            for (int j = 0; j <= c - 1; j++)
                cl[i][j] = cl[i][j].mul(f);
        }
        return ret;
    }

    /**
     * ���������
     * 
     * @param o �����Ĳ�������
     * @return {@code this - o}
     * @throws MatrixWrongSizeException ��{@code this}��{@code o}����ͬ�;���
     */
    public Matrix sub(Matrix o) {
        if (rows == o.rows && cols == o.cols) {
            Fraction[][] oFrac = o.getValue();
            Fraction[][] retFrac = new Fraction[rows][cols];
            for (int i = 0; i <= rows - 1; i++)
                for (int j = 0; j <= cols - 1; j++)
                    retFrac[i][j] = num[i][j].sub(oFrac[i][j]);
            return new Matrix(retFrac);
        } else {
            throw new MatrixWrongSizeException();
        }
    }

    /**
     * ���ؾ�����ַ�����ʽ���������з���
     * <p>
     * {@inheritDoc}
     * 
     * @see #toStringLinear()
     */
    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i <= rows - 1; i++) {
            for (int j = 0; j <= cols - 1; j++)
                ret = ret + num[i][j] + " ";
            if (i != rows - 1)
                ret = ret + "\n";
        }
        return ret;
    }

    /**
     * ���ؾ�����ַ�����ʽ�����������з���
     * 
     * @see #toString()
     */

    public String toStringLinear() {
        String ret = "";
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                ret = ret + num[i][j] + " ";
        return ret;
    }

    /**
     * ����ת�þ���
     * 
     * @return ת�þ���
     */
    public Matrix trans() {
        Fraction[][] retn = new Fraction[cols][rows];
        for (int i = 0; i <= rows - 1; i++)
            for (int j = 0; j <= cols - 1; j++)
                retn[j][i] = num[i][j];
        return new Matrix(retn);
    }

    /**
     * ������׼�;���
     * 
     * @return ��׼�;���
     */
    public Matrix getStandard() {
        Matrix ret = getRowSimplified();
        Fraction[][] val = ret.getValue();
        int rows = ret.getRows();
        int cols = ret.getCols();
        int posR = 0;
        do {
            int posC = 0;
            while (posC < cols)
                if (val[posR][posC].getA() == 1)
                    break;
                else
                    posC++;
            posC++;
            while (posC < cols) {
                if (val[posR][posC].getA() != 0)
                    val[posR][posC] = new Fraction(0);
                posC++;
            }
            posR++;
        } while (posR < rows);
        return ret;
    }
}