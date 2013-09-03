package br.com.javamagazine.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.javamagazine.client.Contato;
import br.com.javamagazine.client.ContatoResourceFacade;


public class ContatoPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final ContatoResourceFacade facade;
	private final JTextField celularJTextField;
	private final JTextField idJTextField;
	private final JTextField nomeJTextField;
	private final JTextField telefoneJTextField;
	private final JLabel lblFoto1;

	public ContatoPanel(final Contato newContato) {
		this();
		setContato(newContato);
	}

	public ContatoPanel() {
		facade = new ContatoResourceFacade();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 246, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4 };
		setLayout(gridBagLayout);

		JLabel idLabel = new JLabel("Id:");
		GridBagConstraints labelGbc_1 = new GridBagConstraints();
		labelGbc_1.anchor = GridBagConstraints.EAST;
		labelGbc_1.insets = new Insets(5, 5, 5, 5);
		labelGbc_1.gridx = 0;
		labelGbc_1.gridy = 0;
		add(idLabel, labelGbc_1);

		idJTextField = new JTextField();
		idJTextField.setEditable(false);
		GridBagConstraints componentGbc_1 = new GridBagConstraints();
		componentGbc_1.insets = new Insets(5, 0, 5, 5);
		componentGbc_1.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_1.gridx = 1;
		componentGbc_1.gridy = 0;
		add(idJTextField, componentGbc_1);


		JLabel nomeLabel = new JLabel("Nome:");
		GridBagConstraints labelGbc_2 = new GridBagConstraints();
		labelGbc_2.anchor = GridBagConstraints.EAST;
		labelGbc_2.insets = new Insets(5, 5, 5, 5);
		labelGbc_2.gridx = 0;
		labelGbc_2.gridy = 1;
		add(nomeLabel, labelGbc_2);

		nomeJTextField = new JTextField();
		GridBagConstraints componentGbc_2 = new GridBagConstraints();
		componentGbc_2.insets = new Insets(5, 0, 5, 5);
		componentGbc_2.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_2.gridx = 1;
		componentGbc_2.gridy = 1;
		add(nomeJTextField, componentGbc_2);


		JLabel celularLabel = new JLabel("Celular:");
		GridBagConstraints labelGbc_0 = new GridBagConstraints();
		labelGbc_0.anchor = GridBagConstraints.EAST;
		labelGbc_0.insets = new Insets(5, 5, 5, 5);
		labelGbc_0.gridx = 0;
		labelGbc_0.gridy = 2;
		add(celularLabel, labelGbc_0);

		celularJTextField = new JTextField();
		GridBagConstraints componentGbc_0 = new GridBagConstraints();
		componentGbc_0.insets = new Insets(5, 0, 5, 5);
		componentGbc_0.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_0.gridx = 1;
		componentGbc_0.gridy = 2;
		add(celularJTextField, componentGbc_0);


		JLabel telefoneLabel = new JLabel("Telefone:");
		GridBagConstraints labelGbc_3 = new GridBagConstraints();
		labelGbc_3.anchor = GridBagConstraints.EAST;
		labelGbc_3.insets = new Insets(5, 5, 5, 5);
		labelGbc_3.gridx = 0;
		labelGbc_3.gridy = 3;
		add(telefoneLabel, labelGbc_3);

		telefoneJTextField = new JTextField();
		GridBagConstraints componentGbc_3 = new GridBagConstraints();
		componentGbc_3.insets = new Insets(5, 0, 5, 5);
		componentGbc_3.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_3.gridx = 1;
		componentGbc_3.gridy = 3;
		add(telefoneJTextField, componentGbc_3);


		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 4;
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(5, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));


		lblFoto1 = new JLabel("Foto");
    	lblFoto1.setPreferredSize(new Dimension(1,1));
    	lblFoto1.setMinimumSize(new Dimension(1,1));

		lblFoto1.addMouseListener(fotoAction);
		lblFoto1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblFoto1, BorderLayout.CENTER);



		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 4;
		gbc_table.insets = new Insets(5, 5, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 5;
		final SimpleTable<Contato> table = new SimpleTable<>(new ContatoTableModel());
		scrollPane.setViewportView(table);
		add(scrollPane, gbc_table);

		ListSelectionModel cellSelectionModel = table.getSelectionModel();
	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent e) {
                    Contato c = table.getSelectedElement();
                    if(c!=null) {
                        setContato(c);
                    }
			}
		});

        List<Contato> list = facade.buscarTodos();
        table.setElements(list);


		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(final ActionEvent e) {
				setContato(new Contato());
			}
		});
		GridBagConstraints gbc_btnLimpar = new GridBagConstraints();
		gbc_btnLimpar.anchor = GridBagConstraints.EAST;
		gbc_btnLimpar.insets = new Insets(0, 0, 5, 5);
		gbc_btnLimpar.gridx = 1;
		gbc_btnLimpar.gridy = 4;
		add(btnLimpar, gbc_btnLimpar);


		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(final ActionEvent e) {
				Contato contato = getContato();
				if(contato.getId()==null){
					setContato(facade.salvar(contato));
					JOptionPane.showMessageDialog(ContatoPanel.this, "Contato criado com suceso!");
				} else {
					facade.salvar(contato);
					table.setElement(contato);
					JOptionPane.showMessageDialog(ContatoPanel.this, "Contato alterado com suceso!");
				}
			}
		});
		GridBagConstraints gbc_btnSalvar = new GridBagConstraints();
		gbc_btnSalvar.insets = new Insets(0, 0, 5, 5);
		gbc_btnSalvar.anchor = GridBagConstraints.EAST;
		gbc_btnSalvar.gridx = 2;
		gbc_btnSalvar.gridy = 4;
		add(btnSalvar, gbc_btnSalvar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(final ActionEvent e) {
				Contato contato = getContato();
				if(contato.getId()!=null){
					facade.remover(contato.getId());
					table.removeElement(contato);
					setContato(new Contato());
					JOptionPane.showMessageDialog(ContatoPanel.this, "Contato excluído com suceso!");
				}
			}
		});
		GridBagConstraints gbc_btnExcluir = new GridBagConstraints();
		gbc_btnExcluir.insets = new Insets(0, 0, 5, 5);
		gbc_btnExcluir.anchor = GridBagConstraints.EAST;
		gbc_btnExcluir.gridx = 3;
		gbc_btnExcluir.gridy = 4;
		add(btnExcluir, gbc_btnExcluir);



		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(final ActionEvent e) {
				String nome = getContato().getNome();
				Contato contatoPesquisar = new Contato();
				contatoPesquisar.setNome(nome);
				setContato(contatoPesquisar);
				table.setElements(facade.pesquisarPor(nome));
			}
		});
		GridBagConstraints gbc_btnPesquisar = new GridBagConstraints();
		gbc_btnPesquisar.insets = new Insets(0, 5, 5, 5);
		gbc_btnPesquisar.gridx = 0;
		gbc_btnPesquisar.gridy = 4;
		add(btnPesquisar, gbc_btnPesquisar);
	}

	public Contato getContato() {
		Contato contato = new Contato();
		String id = idJTextField.getText();
		contato.setId(id==null || "".equals(id.trim())? null : Integer.valueOf(id));
		contato.setNome(nomeJTextField.getText());
		contato.setTelefone(telefoneJTextField.getText());
		contato.setCelular(celularJTextField.getText());
		return contato;
	}

	public void setContato(final Contato contato) {
		Integer id = contato.getId();
		idJTextField.setText(id==null? "" : "" + id);
		nomeJTextField.setText(contato.getNome());
		telefoneJTextField.setText(contato.getTelefone());
		celularJTextField.setText(contato.getCelular());

		ImageIcon icon = null;
		if(id!=null) {
            byte[] bytes = facade.downloadFoto(id);
            if(bytes.length>0){
                icon = new ImageIcon(bytes);
            }
		}
        setFoto(icon);
	}

	MouseAdapter fotoAction = new MouseAdapter() {
		@Override
		public void mouseClicked(final MouseEvent e) {
			JFileChooser fc = new JFileChooser(){
                private static final long serialVersionUID = 1L;

                @Override
                public boolean accept(final File f) {
				    if (f.isDirectory()) {
				        return true;
				    }

				    String extension = Utils.getExtension(f);
				    if (extension != null) {
				        if (extension.equals(Utils.tiff) || extension.equals(Utils.tif) || extension.equals(Utils.gif) ||
				            extension.equals(Utils.jpeg) || extension.equals(Utils.jpg) || extension.equals(Utils.png)) {
				            return true;
				        } else {
				            return false;
				        }
				    }

				    return false;
				}
			};
			fc.setAcceptAllFileFilterUsed(false);

			changeFoto(fc);
		}
	};

	private void changeFoto(final JFileChooser fc) {
		Integer id = getContato().getId();
		if(id!=null) {
			int returnVal = fc.showOpenDialog(ContatoPanel.this);
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            try {
	            	BufferedImage foto = ImageIO.read(file);
	            	ImageIcon icon = new ImageIcon(foto);
					setFoto(icon);

	            	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            	ImageIO.write(foto, Utils.getExtension(file), baos);
	            	baos.flush();
	            	byte[] bs = baos.toByteArray();
	            	baos.close();
	            	facade.uploadFoto(id, bs);
	            	JOptionPane.showMessageDialog(ContatoPanel.this, "Foto salva com sucesso!");
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
        }
	}

    private void setFoto(final ImageIcon icon) {
        lblFoto1.setIcon(icon);
        if(icon!=null){
            lblFoto1.setText("");
        } else {
            lblFoto1.setText("Foto");
        }
        lblFoto1.revalidate();
    }

	static class Utils {

	    public final static String jpeg = "jpeg";
	    public final static String jpg = "jpg";
	    public final static String gif = "gif";
	    public final static String tiff = "tiff";
	    public final static String tif = "tif";
	    public final static String png = "png";

	    public static String getExtension(final File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        return ext;
	    }
	}

	public static class ContatoTableModel extends SimpleTableModel<Contato> {
		private static final long serialVersionUID = -8324381312293150101L;
		private static final String[] HEADERS = new String[] { "Id", "Nome", "Celular"};
		private static final Integer[] COLUMNSIZE = new Integer[] { 0, 440, 50 };

		public ContatoTableModel() {
			super(HEADERS);
		}

		@Override
		public Object getValue(final Contato c, final int columnIndex) {
			switch (columnIndex) {
				case 0:
					return c.getId();
				case 1:
					return c.getNome();
				case 2:
					return c.getCelular();
				default:
					return "";
			}
		}

		@Override
		public Integer[] getColumnSize() {
			return COLUMNSIZE;
		}
	}
}
