import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

public class AddBus {

JTextField  nam, speed;
JLabel b,ts;
JFrame frame;
JButton okcore, notecore ;
   
public AddBus(){

	frame = new JFrame("Bus");
	
	frame.setSize(250,150);
	frame.setLocation(500,100);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setVisible(true);
	
	Box mainBox = Box.createVerticalBox();
	
    Box box1 = Box.createHorizontalBox();
    b = new JLabel("Name Bus");
	nam = new JTextField(15);
	box1.add(b);
	box1.add(Box.createHorizontalStrut(6));
	box1.add(nam);
 	 	 	 
 	Box box3 = Box.createHorizontalBox();
	ts = new JLabel("Speed");
	speed= new JTextField(15);
	box3.add(ts);
	box3.add(Box.createHorizontalStrut(6));
	box3.add(speed);
	
 	Box box2 = Box.createHorizontalBox();
 	okcore = new JButton ("��");
 	notecore = new JButton ("������");
 	box2.add(okcore);
 	box2.add(notecore); 	
 	
    mainBox.setBorder(new EmptyBorder(12,12,12,12));
    mainBox.add(box1);
    mainBox.add(Box.createVerticalStrut(12));
    mainBox.add(box3);
    mainBox.add(Box.createVerticalStrut(12));
    mainBox.add(box2);

	frame.add(mainBox);
	DDDD AD = new DDDD();
	okcore.addActionListener(AD);
    notecore.addActionListener(AD);
	
	}

	private URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/called_com.vogella.jersey.jaxb").build();
	}

	class DDDD implements ActionListener {

		public void actionPerformed(ActionEvent ev){
			if (ev.getSource() == okcore) {
				 int f=0;
				String busName = nam.getText();
				String sp = speed.getText();
				 if(busName.trim().length()>0 && sp.trim().length()>0){
					ClientConfig config = new ClientConfig();
					Client client = ClientBuilder.newClient(config);
					System.out.println(busName);
					WebTarget target = client.target(getBaseURI());
					String Response = "";
					try{
						Response = target.path("rest").path("add").path("bus").path(busName).path(sp).path(Frame1.systemIndex).request().accept(MediaType.TEXT_PLAIN).get(String.class);
						f=1;
					}catch(NotFoundException e){
						JLabel countLabel = new JLabel("��������� ��������� ������!"); 
						JOptionPane.showMessageDialog(null, countLabel);
					}
					if(Response.equals("ERROR")){
						f=0;
						JLabel countLabel = new JLabel("����� ��� ��� ������!"); 
			            JOptionPane.showMessageDialog(null, countLabel);
					}
					System.out.println(Response);
					
					while(Frame1.model.getRowCount() > 0){
						Frame1.model.removeRow(0);
					}
					String Response1 = "";
					try{
						Response1 = target.path("rest").path("getinfo").path("bus").path(Frame1.systemIndex).request().accept(MediaType.TEXT_PLAIN).get(String.class);
					}
					catch(ProcessingException e){
			        	JLabel countLabel = new JLabel("��� ����������� � �������!"); 
						JOptionPane.showMessageDialog(null, countLabel);
			        }
					System.out.println(Response1);
					String dataM [] = Response1.split("-");
					System.out.println(dataM.length);
					if(dataM.length>1){
						for(int i =1; i<dataM.length;i++){
							System.out.println(dataM[i]);
						}
						//data = new Object [dataM.length-1][3];
			
						for(int i = 1; i<dataM.length;i++){
							//data[i-1] = dataM[i].split(":");
							Frame1.model.addRow(dataM[i].split(":"));
						} 
						
					}
					if(f==1)
						frame.dispose();
					 
				 }
				 else {
					 JLabel countLabel = new JLabel("�� ��� ���� ���������!"); 
		             JOptionPane.showMessageDialog(null, countLabel);
				 }
			 
			}
			if (ev.getSource() == notecore) {
			 
				frame.dispose();
			 
			}
		 
		}
	}	
}