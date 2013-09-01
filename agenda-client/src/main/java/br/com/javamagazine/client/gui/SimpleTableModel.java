package br.com.javamagazine.client.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public abstract class SimpleTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = -3987806589802814863L;

	private String[] headers;
	protected List<T> modelList;
	protected Boolean[] selected = new Boolean[] {};

	public SimpleTableModel(final String[] columnHeaders) {
		this(columnHeaders, new ArrayList<T>());
	}

	public SimpleTableModel(final String[] columnHeaders, final List<T> modelObjectList) {
		super();
		this.headers = columnHeaders;
		setModelList(modelObjectList);
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		return super.getColumnClass(columnIndex);
	}

	@Override
	public String getColumnName(final int col) {
		return headers[col];
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public int getRowCount() {
		return modelList.size();
	}

	public String[] getHeaders() {
		return this.headers;
	}

	public List<T> getModelList() {
		return modelList;
	}

	public T getModel(final int rowIndex) {
		return modelList.get(rowIndex);
	}

	public void setModelList(final List<T> modelObjectList) {
		if (modelObjectList == null) {
			this.modelList = new ArrayList<T>();
		} else {
			this.modelList = modelObjectList;
		}
		this.initSelectedCells();
		fireTableDataChanged();
	}

	public void setModel(final int rowIndex, final T model) {
		int size = this.modelList.size();
		if (size > 0 && rowIndex < size) {
			this.modelList.set(rowIndex, model);
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
	}

	public void setModel(final T el) {
        int index = 0;
        for(T m : this.modelList) {
            if(m.equals(el)){
                break;
            }
            index++;
        }
        setModel(index, el);
    }

	public void removeModel(final int rowIndex) {
		int size = this.modelList.size();
		if (size > 0 && rowIndex < size) {
			this.modelList.remove(rowIndex);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}
	}

	public void removeModel(final T el) {
	    int index = 0;
	    for(T m : this.modelList) {
	        if(m.equals(el)){
	            break;
	        }
	        index++;
	    }
	    removeModel(index);
    }

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		T object = getModel(rowIndex);
		return getValue(object, columnIndex);
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		return false;
	}

	protected void initSelectedCells() {
		if (this.modelList != null) {
			this.selected = new Boolean[this.modelList.size()];
			for (int i = 0; i < this.selected.length; i++) {
				this.selected[i] = Boolean.FALSE;
			}
		}
	}

	public abstract Object getValue(T object, int columnIndex);

	public abstract Integer[] getColumnSize();

}