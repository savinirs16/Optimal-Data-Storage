import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.sql.*;
import java.nio.charset.*;
class Store extends Panel implements ActionListener
{
	Image img,resizedimage;
	JFileChooser fc=new JFileChooser();
	ImageIcon image1;
	String pathname,pathname_save;
	boolean bws_scc=false;
	JButton button_open,button_save;
	JPanel bp,ip,pnl;
	JLabel l1,bg;
	FileInputStream imageinFile;
	byte[] imageData,imageData1,bindata;
	Connection con;
	Statement stmt,stmt1;
	String uni_id;
	StringBuffer sb=new StringBuffer();
	public Store()
    	{
			try {

        				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                			String accessFileName = "E:/aanew/Database1";
                                 	                String connURL="jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+accessFileName+".accdb;";
		              con = DriverManager.getConnection(connURL, "","");
	 		     }
			    catch (Exception err) 
			    {
				        System.out.println("ERROR: " + err);
    		            }
		bg=new  JLabel(new ImageIcon("N:/TAK/cp.jpg"));
		bg.setLayout(new BorderLayout());
		//bg.setSize(1400,700);
		setLayout(new BorderLayout());
		bp=new JPanel();
		Font f1=new Font("Times New Roman",Font.ITALIC,30);
		button_open=new JButton("Browse");
		button_open.setFont(f1);
		Font f2=new Font("Times New Roman",Font.ITALIC,30);
		button_save=new JButton("Save");
		button_save.setFont(f2);
		bp.setLayout(new BoxLayout(bp,BoxLayout.Y_AXIS));
		bp.add(Box.createRigidArea(new Dimension(50, 0)));
		bp.add(Box.createRigidArea(new Dimension(0, 100)));
        		bp.add(button_open);
		bp.add(Box.createRigidArea(new Dimension(0, 200)));
        		bp.add(button_save);
		add(bp, BorderLayout.WEST);
		
		//**************Image section	
		pnl=new JPanel();	
		pnl.setSize(300,300);
		l1=new JLabel("image",JLabel.CENTER);
		l1.setSize(150,100);
		l1.setPreferredSize(new Dimension(500,500));
		pnl.add(l1);
		pnl.setOpaque(false);
		add(pnl, BorderLayout.CENTER);
		bg.add(pnl,BorderLayout.CENTER);
		bg.add(bp,BorderLayout.WEST);
		//bg.add(ip,BorderLayout.CENTER);
		bp.setOpaque(false);
		//ip.setOpaque(false);
		add(bg);
		button_open.addActionListener(this);
		button_save.addActionListener(this);
		

	}
	public void actionPerformed(ActionEvent ae)
	{
	File file;
	  //BufferedImage bi;
	  if(ae.getSource()==button_open)
	  {
		  
		  try
		  {
			  l1.setIcon(null);
			  fc.showOpenDialog(this);
		        file = fc.getSelectedFile();
		        pathname = file.getPath();
		        img=ImageIO.read(fc.getSelectedFile());
			//System.out.println(img.getWidth(null)+"img.heig--"+img.getHeight(null)+"l1.width-->"+l1.getWidth()+"l1.heh--"+l1.getHeight());
		        if(img.getWidth(null)>l1.getWidth() && img.getHeight(null)>l1.getHeight()){
			       //System.out.println("entered into resized");
			        resizedimage=img.getScaledInstance(l1.getWidth(), l1.getHeight(), 0);
			        l1.setIcon(new ImageIcon(resizedimage));
	 		}
        		else{
			        l1.setIcon(new ImageIcon(img));}
			         if(img.equals(null))
				bws_scc=true;
				l1.setText("");
          		     }
		  catch(Exception e){}
		  }
		  else if(ae.getSource()==button_save)
		  {
			  try
			  {
				algorithm();
				
	                                   }
			  catch(Exception e){}
		   }
	
	}
public void imgtobnry(String s) throws IOException
{
	File file = new File(s);
	 
    try {           
        // Reading a Image file from file system
         imageinFile = new FileInputStream(file);
         imageData = new byte[(int) file.length()];
        imageinFile.read(imageData);
        imageData1=imageData;
                
         }
         catch(Exception e){}
    finally{
    	imageinFile.close();
             }
}
public void algorithm() throws IOException,Exception
{
	imgtobnry(pathname);
	if(imageData1==null)
	  {
		  System.out.println("entered into cond");
		  JOptionPane.showMessageDialog(null,"please select an valid image");
	  }
	bindata=new byte[imageData.length];
	bindata=imageData1;
	System.out.println(bindata);
	for(int i=0;i<bindata.length;i++)
	{
		sb.append(bindata[i]+" ");
	}
	System.out.println(sb);
	storng();
	System.out.println("The image had been successfully stored");
	JOptionPane.showMessageDialog(null,"Your Unique Id:  "+uni_id);
         
}
public void storng() throws Exception
{
	try{
	 	stmt = con.createStatement();
		stmt.execute("insert into Table1(Field1) values('"+sb+"')"); 
		stmt1=con.createStatement();
		stmt1.execute("select ID from Table1 where Field1='"+sb+"'"); // execute query in table ODS

		ResultSet rs = stmt1.getResultSet(); // get any Result that came from our query

		if (rs != null)
		while ( rs.next() ){
			//bindata=rs.getString("Data").getBytes();
				uni_id=rs.getString("ID");
			
			System.out.println(" ID: "+uni_id);
         			}
	     }
	catch (Exception err) {
		        System.out.println("ERROR: " + err);
                                                }
	finally{
         	stmt.close();
		stmt1.close();
         	con.close();
                      }

}

}
class Retrieve extends Panel implements ActionListener
{
	JButton btn_ret;
	JLabel lbl,l1,bg;
	JTextField txtfld;
	JPanel p1,p2;
	BufferedImage bi1;
	Image img,resizedimage;
	ImageIcon image1;
	byte[] imageData,imageData1,bindata;
	Connection con;
	Statement stmt;
	String uni_id;
	int  unqid;
	public Retrieve()
	{
		try {

        				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                			String accessFileName = "E:/aanew/Database1";
                                 	                String connURL="jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+accessFileName+".accdb;";
		              con = DriverManager.getConnection(connURL, "","");
	 		     }
			    catch (Exception err) 
			    {
				        System.out.println("ERROR: " + err);
    		            }

		bg=new  JLabel(new ImageIcon("N:/TAK/cp.jpg"));
		bg.setLayout(new BorderLayout());
		bg.setSize(1500,800);
		Font f1=new Font("Times New Roman",Font.ITALIC,30);
		setLayout(new BorderLayout());
		txtfld=new JTextField(10);
		lbl=new JLabel("Unique Id");
		btn_ret=new JButton("Retrieve");
		btn_ret.setFont(f1);
		p1=new JPanel();
		p1.add(lbl);
		p1.add(txtfld);
		p1.add(btn_ret);
		add(p1,BorderLayout.SOUTH);
		txtfld.setFont(f1);
		p2=new JPanel();
		l1=new JLabel("image",JLabel.CENTER);
		l1.setSize(300,300);
		l1.setPreferredSize(new Dimension(300,300));
		p2.add(l1);
		bg.add(p2, BorderLayout.CENTER);
		bg.add(p1,BorderLayout.SOUTH);
		p1.setOpaque(false);
		p2.setOpaque(false);
		add(bg);
		btn_ret.addActionListener(this);
		setSize(1400,700);
	}
public void bnrytoimg() throws IOException
{
	    Image resizedimage1;
    	bi1=ImageIO.read(new ByteArrayInputStream(bindata));
    	image1=new ImageIcon(bi1);
    	if(bi1.getWidth(null)>l1.getWidth() && bi1.getHeight(null)>l1.getHeight()){
    	resizedimage1=bi1.getScaledInstance(l1.getWidth(), l1.getHeight(), 0);
	//JOptionPane.showMessageDialog(null,"The image had been successfully modified");
    	l1.setIcon(new ImageIcon(resizedimage1));
    	}
    	else{
	//JOptionPane.showMessageDialog(null,"The image had been successfully modified");
    	l1.setIcon(image1);}
    	//imageoutFile.close();
    //}
}
public void actionPerformed(ActionEvent ae)
{
	unqid=Integer.parseInt(txtfld.getText());
	if(ae.getSource()==btn_ret)
	{
		
		System.out.println("val : "+txtfld.getText());
		if(txtfld.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null,"Please provide a valid unique id");
		}
		else{
			try{
			retrve();
			}
			catch(Exception e){}
		}
	}	
}
public void retrve()throws Exception
{
	ByteArrayOutputStream bout=new ByteArrayOutputStream();
	try{
		System.out.println("Entered into retrve");
	 	stmt = con.createStatement();
			//System.out.println(stmt.toString());
		stmt.execute("select * from Table1 where ID="+unqid); // execute query in table ODS

		ResultSet rs = stmt.getResultSet(); // get any Result that came from our query

		if (rs != null)
		{
			System.out.println("rs is not null");
		while ( rs.next() ){
			//bout.write(rs.getString("Data").getBytes(StandardCharsets.UTF_8));
			//bindata=bout.toByteArray();
			bindata=rs.getString("Feild1").getBytes(StandardCharsets.UTF_8);
			System.out.println(bindata);
			bnrytoimg();
			 }
		}
		else{ System.out.println(rs.toString());}
	     }
	catch (Exception err) {
		        System.out.println("ERROR: " + err);
                              }
	finally{
         	stmt.close();
         	con.close();
                      }


}
}
/*class Home extends Panel
{
	JButton btn_store,btn_retr;
	JLabel bg;
	Panel p1;
	public Home()
	{	
		bg=new  JLabel(new ImageIcon("Z:/imagedatahider/cp.jpg"));
		bg.setLayout(new BorderLayout());
		bg.setSize(1360,600);
		setLayout(new BorderLayout());
		p1=new Panel();
		btn_store =new JButton("Store");
		btn_retr=new JButton("Retrieve");
		btn_store.setSize(50,50);
		btn_retr.setSize(50,50);
		p1.add(btn_store);
		p1.add(btn_retr);
		add(p1,BorderLayout.CENTER);
		bg.add(p1,BorderLayout.CENTER);
		p1.setOpaque(false);
		add(bg);
		//btn_store.addActionListener(CardLayoutDemo);
		//btn_retr.addActionListener(CardLayoutDemo);
	}
}*/
class ODS extends JFrame implements ActionListener 
{
    JButton bu1;
    JButton btn_store,btn_retr;
    JLabel bg,titl; 
    Panel cards,p1,home,p2;
    CardLayout c;
    Store pa1;
    Retrieve pa2;
    public ODS(String title)
    {
	 	super(title);	
		Font f1=new Font("Times New Roman",Font.ITALIC,30);
		Font f2=new Font("Times New Roman",Font.ITALIC,50);
		bg=new  JLabel(new ImageIcon("N:/TAK/cp.jpg"));
		bg.setLayout(new BorderLayout());
		bg.setSize(1360,700);
		home=new Panel();
	                home.setLayout(new BorderLayout());
		p2=new Panel();
		btn_store =new JButton("Store");
		btn_retr=new JButton("Retrieve");
		btn_store.setSize(500,500);
		btn_retr.setSize(50,50);
		p2.setLayout(new BoxLayout(p2,BoxLayout.X_AXIS));
		p2.add(Box.createRigidArea(new Dimension(350, 0)));
		p2.add(Box.createRigidArea(new Dimension(110, 0)));
        	               p2.add(btn_store);
		p2.add(Box.createRigidArea(new Dimension(200,0)));
                  	p2.add(btn_retr);
		//p2.add(btn_store);
		//p2.add(btn_retr);
		btn_store.setFont(f1);
		btn_retr.setFont(f1);
		bg.add(p2,BorderLayout.CENTER);
		home.add(bg,BorderLayout.CENTER);
		btn_store.addActionListener(this);
		btn_retr.addActionListener(this);

       
	setLayout(new BorderLayout());
	p1=new Panel();
	titl=new JLabel("Optimal Data Storage");
	titl.setFont(f2);
	bu1=new JButton("Home");
	bu1.setBounds(0,0,170,40);
	add(bu1,BorderLayout.NORTH);
	p1.add(titl);
	bu1.setFont(f1);
	bu1.setBackground(Color.WHITE);
	add(p1,BorderLayout.NORTH);
        cards=new Panel();
        pa1=new Store();
       	pa2=new Retrieve();
	c=new CardLayout();
	
        cards.setLayout(c);
	cards.add(home,"Home");        
	cards.add(pa1,"Store");
     	cards.add(pa2,"Retrieve");
	bu1.addActionListener(this);
	

//      addWindowListener(new MyWindowListener());
       
        add("Center",cards);
        setSize(1360,730);
        setVisible(true);
    }
	public void actionPerformed(ActionEvent ae)
	{
	if(ae.getSource()==btn_store)
          	c.show(cards, "Store");
        if(ae.getSource()==btn_retr)
            c.show(cards,"Retrieve");
	if(ae.getSource()==bu1)
		c.show(cards,"Home");
	}
    public static void main(String args[])
    {
        new ODS("Optimal Data Storage");
    }
}

	