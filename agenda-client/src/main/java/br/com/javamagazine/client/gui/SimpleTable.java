package br.com.javamagazine.client.gui;

import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


public class SimpleTable<T> extends JTable {

	private static final long serialVersionUID = 2289940932856569516L;

	private SimpleTableModel<T> model;

	public SimpleTable(final SimpleTableModel<T> tableModel) {
		super(tableModel);

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(false);

		TableColumn tc = this.getColumnModel().getColumn(0);
		tc.setMinWidth(30);
		tc.setMaxWidth(30);
		tc.setPreferredWidth(30);
		tc.setResizable(false);
	}

    @SuppressWarnings("unchecked")
	@Override
    public void setModel(final TableModel dataModel)  {
        if ( dataModel instanceof SimpleTableModel<?> ){
            super.setModel(dataModel);
            this.model = (SimpleTableModel<T>) dataModel;
        } else {
            throw new RuntimeException("Esta SimpleTable somente trabalha com SimpleTableModel!");
        }
    }

    public void setElements(final List<T> modelList) {
        model.setModelList(modelList);
    }

    public void setElement(final T el ){
        model.setModel(el);
    }

    public void removeElement(final T el){
        model.removeModel(el);
    }

    public T getSelectedElement(){
        int rowIndex = getSelectedRow();
        if( rowIndex==-1 ){
            return null;
        }
        return model.getModel(rowIndex);
    }
}