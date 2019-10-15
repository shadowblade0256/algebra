package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import matrix.Matrix;
import storage.MatrixStorage;

public class EditMatrix extends JFrame {

	private static final long serialVersionUID = -7942082466574502029L;
	private JPanel contentPane = new JPanel();
	private JTextField textFieldVarName = new JTextField();
	GuiMain gui;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditMatrix frame = new EditMatrix(-1,);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	JButton buttonDone = new JButton("\u5B8C\u6210");

	JButton buttonCancel = new JButton("\u53D6\u6D88");

	JButton buttonClear = new JButton("\u6E05\u7A7A");

	JLabel labelVarName = new JLabel("\u77E9\u9635\u53D8\u91CF\u540D");

	JLabel labelRow = new JLabel("\u884C");

	JLabel labelColumn = new JLabel("\u5217");

	JComboBox<String> comboBoxRow = new JComboBox<String>();

	JComboBox<String> comboBoxColumn = new JComboBox<String>();

	JScrollPane scrollPane = new JScrollPane();

	JTextArea textArea = new JTextArea();

	/**
	 * Create the frame.
	 */
	public EditMatrix(GuiMain gui) {
		this.gui = gui;
		setTitle("�½�����");
		init(-1);
		buttonDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MatrixStorage newMat;
				try {
					newMat = new MatrixStorage(
							Matrix.matrixFromString(comboBoxRow.getSelectedIndex() + 1,
									comboBoxColumn.getSelectedIndex() + 1, textArea.getText()),
							textFieldVarName.getText());
					if (textFieldVarName.getText().equals("")) {
						JOptionPane.showMessageDialog(EditMatrix.this, "�������������Ϊ�ա�", "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (checkSameName(null)) {
						JOptionPane.showMessageDialog(EditMatrix.this, "���������ظ���", "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					gui.manager.add(newMat);
					gui.updateListCore();
					gui.manager.saveToFile(newMat);
					gui.list.setSelectedIndex(gui.list.getLastVisibleIndex());
					EditMatrix.this.dispose();
				} catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(EditMatrix.this, "��������ʽ����", "����", JOptionPane.ERROR_MESSAGE);
				} catch (NoSuchElementException g) {
					JOptionPane.showMessageDialog(EditMatrix.this, "�������������㡣", "����", JOptionPane.ERROR_MESSAGE);
				} catch (IOException h) {
					JOptionPane.showMessageDialog(EditMatrix.this, textArea.getText() + ".matdat����ʧ�ܡ�", "����",
							JOptionPane.ERROR_MESSAGE);
					EditMatrix.this.dispose();
				}
			}
		});

	}

	/**
	 * @wbp.parser.constructor
	 */
	public EditMatrix(int index, GuiMain gui) {
		this.gui = gui;
		setTitle("�༭����");
		MatrixStorage mat = gui.manager.get(index);
		textFieldVarName.setText(mat.getName());
		textArea.setText(mat.getMat().toString());
		init(index);
		comboBoxRow.setSelectedIndex(mat.getMat().getRows() - 1);
		comboBoxColumn.setSelectedIndex(mat.getMat().getCols() - 1);
		buttonDone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MatrixStorage newMat;
				try {
					newMat = new MatrixStorage(
							Matrix.matrixFromString(comboBoxRow.getSelectedIndex() + 1,
									comboBoxColumn.getSelectedIndex() + 1, textArea.getText()),
							textFieldVarName.getText());
					if (checkSameName(mat.getName())) {
						JOptionPane.showMessageDialog(EditMatrix.this, "�����������ظ���", "����", JOptionPane.ERROR_MESSAGE);
						return;
					}
					gui.manager.changeMatrix(index, newMat);
					gui.updateViewer(index);
					gui.updateListCore();
					gui.list.setSelectedIndex(index);
					EditMatrix.this.dispose();
				} catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(EditMatrix.this, "��������ʽ����", "����", JOptionPane.ERROR_MESSAGE);
				} catch (NoSuchElementException g) {
					JOptionPane.showMessageDialog(EditMatrix.this, "�������������㡣", "����", JOptionPane.ERROR_MESSAGE);
				} catch (IOException h) {
					JOptionPane.showMessageDialog(EditMatrix.this, "����", textArea.getText() + ".matdat����ʧ�ܡ�",
							JOptionPane.ERROR_MESSAGE);
					EditMatrix.this.dispose();
				}
			}
		});

	}

	private void init(int index) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 300, 400);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(null);

		buttonDone.setBounds(108, 328, 70, 23);

		buttonCancel.setBounds(204, 328, 70, 23);

		contentPane.add(buttonCancel);

		buttonClear.setBounds(10, 328, 70, 23);
		buttonClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		contentPane.add(buttonClear);

		labelVarName.setBounds(10, 13, 70, 15);

		contentPane.add(labelVarName);

		labelRow.setBounds(94, 41, 24, 15);

		contentPane.add(labelRow);

		labelColumn.setBounds(223, 41, 24, 15);

		contentPane.add(labelColumn);

		comboBoxRow.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));

		comboBoxRow.setBounds(45, 38, 32, 21);

		contentPane.add(comboBoxRow);

		comboBoxColumn
				.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));

		comboBoxColumn.setBounds(167, 41, 32, 21);

		contentPane.add(comboBoxColumn);

		scrollPane.setBounds(10, 71, 264, 247);

		contentPane.add(scrollPane);

		scrollPane.setViewportView(textArea);

		textFieldVarName.setBounds(84, 10, 190, 21);
		textFieldVarName.setColumns(10);
		contentPane.add(textFieldVarName);
		contentPane.add(buttonDone);
		buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditMatrix.this.dispose();
			}
		});
		setVisible(true);
	}

	public boolean checkSameName(String latencyName) {
		boolean ret = false;
		String[] nameList = gui.manager.getNameList();
		if (!textFieldVarName.getText().equalsIgnoreCase(latencyName))
			for (String x : nameList)
				if (textFieldVarName.getText().equalsIgnoreCase(x))
					ret = true;
		return ret;
	}
}
