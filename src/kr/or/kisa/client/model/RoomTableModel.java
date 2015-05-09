/*package kr.or.kisa.client;



import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class roomTableModel extends AbstractTableModel {
	String[] headerName = {"방 번호", "방 이름", " 방 인원", "방장 이름"};
	Vector<String> rows;
	public roomTableModel() {
		this(10);
	}

	public roomTableModel(int capacity) {
		rows = new Vector<String>(capacity, 2);

	}

	@Override
	public int getColumnCount() {
			
		return headerName.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rows.size();
	}
	@Override
	public String getColumnName(int column) {
		return headerName[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		
		switch (columnIndex) {
		case 0: value = "ㅎㅎ"; 	break; // 방번호 삭제 예정
		case 1: value = "asdfasdf";	break; // 방이름
		case 2: value = "asfdasdf"; 	break; // 인원
		case 3: value = "asdfasdfasd";break; // 방장
		}
			
		return value;
	}

	public void setRows(List str){
		rows.clear();
		rows.addAll(str);
		fireTableStructureChanged();
	}
}
 */
package kr.or.kisa.client.model;

import java.io.ObjectInputStream.GetField;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

public class RoomTableModel extends AbstractTableModel {

	Vector<String> headerNames;
	Vector<Room> rooms;

	public RoomTableModel() {
		headerNames = new Vector<String>();
		headerNames.addElement("채팅방이름");
		headerNames.addElement("채팅방장");
		headerNames.addElement("참여인원수");
		headerNames.addElement("최대인원수");
		headerNames.addElement("공개/비공개");
		rooms = new Vector<Room>();
	}

	@Override
	public int getRowCount() {
		return rooms.size();
	}

	@Override
	public int getColumnCount() {
		return headerNames.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object cellData = null;
		switch (columnIndex) {
		case 0:
			cellData = rooms.elementAt(rowIndex).getRoomName();
			break;
		case 1:
			cellData = rooms.elementAt(rowIndex).getRoomMaker();
			break;
		case 2:
			cellData = rooms.elementAt(rowIndex).getEnterCount();
			break;
		case 3:
			cellData = rooms.elementAt(rowIndex).getMaxCount();
			break;
		case 4:
			cellData = rooms.elementAt(rowIndex).getisRock() == 0 ? "공개"
					: "비공개";
			break;
		}
		return cellData;
	}

	@Override
	// 추상 메소드는 아니지만 헤더이름이 나오기위해서는
	// 반드시 재정의 필요
	public String getColumnName(int column) {
		return headerNames.elementAt(column);
	}

	@Override
	public Class getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	public String getOwner(int rowIndex, int columnIndex) {
		return (String) getValueAt(rowIndex, columnIndex);
	}

	public String getisRock(int rowIndex, int columnIndex) {
		return (String) getValueAt(rowIndex, columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * 방추가
	 */
	public void addRoom(Room room) {
		rooms.addElement(room);
		fireTableStructureChanged();
	}

	public void addRoom(String roominfo) {
		rooms.removeAllElements();
		String[] token = roominfo.split("\\/\\/");
		for (int i = 0; i < token.length; i++) {
			String infos = token[i];

			String[] roominfos = infos.split("\\,");
			if (Integer.parseInt(roominfos[2]) == 0) {
				continue;
			}
			rooms.addElement(new Room(roominfos[0], roominfos[1], Integer
					.parseInt(roominfos[2]), Integer.parseInt(roominfos[3]),
					Integer.parseInt(roominfos[4])));
			fireTableStructureChanged();
		}

		fireTableStructureChanged();
	}

	/**
	 * 방목록 초기화
	 */
	public void removeRoom() {
		rooms.removeAllElements();
	}

}
