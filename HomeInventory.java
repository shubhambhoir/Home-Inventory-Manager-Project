package homeInventory;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.*;
import java.awt.print.*;
public class HomeInventory{
	class CloseAction extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			if(JOptionPane.showConfirmDialog(null,"Any unsaved changes will be lost.\nAre you sure you wanna exit?","Exit Program",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION)
				return;
			try {
				PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("inventory.txt")));
				pw.println(n);
				if(n!=0) {
					for(int i=0;i<n;i++) {
						pw.println(obj[i].item);
						pw.println(obj[i].location);
						pw.println(obj[i].id);
						pw.println(obj[i].marked);
						pw.println(obj[i].price);
						pw.println(obj[i].date);
						pw.println(obj[i].store);
						pw.println(obj[i].note);
						pw.println(obj[i].src);
					}
				}
				m=set.size();
				pw.println(m);
				if(m!=0) {
					for(String s:set) pw.println(s);
				}
				pw.close();
			}
			catch(Exception ex) {}
			System.exit(0);
		}
	}
	@SuppressWarnings("serial")
	class PhotoPanel extends JPanel{
		public void paintComponent(Graphics g) {
			Graphics2D g2D=(Graphics2D)g;
			g2D.setPaint(Color.BLACK);
			g2D.draw(new Rectangle2D.Double(0,0,getWidth()-1,getHeight()-1));
			g2D.setColor(Color.WHITE);
			g2D.fillRect(0,0,getWidth()-1,getHeight()-1);
			Image pic=new ImageIcon(src.getText()).getImage();
			int w=getWidth();
			int h=getHeight();
			double wr=(double)getWidth()/(double)pic.getWidth(null);
			double hr=(double)getHeight()/(double)pic.getHeight(null);
			if(wr>hr) w=(int)(pic.getWidth(null)*hr);
			else h=(int)(pic.getHeight(null)*wr);
			g2D.drawImage(pic,(int)(0.5*(getWidth()-w)),(int)(0.5*(getHeight()-h)),w,h,null);
			g2D.dispose();
		}
	}
	class Page implements Printable{
		public int print(Graphics g,PageFormat pf,int pno) {
			Graphics2D g2D=(Graphics2D)g;
			if((pno+1)>l) return NO_SUCH_PAGE;
			int i, iEnd;
			g2D.setFont(new Font("Arial", Font.BOLD, 14));
			g2D.drawString("                                                 Home Inventory Items - Page " + String.valueOf(pno + 1),
			(int) pf.getImageableX(), (int) (pf.getImageableY() + 25));
			int dy = (int) g2D.getFont().getStringBounds("S",g2D.getFontRenderContext()).getHeight();
			int y = (int) (pf.getImageableY() + 4 * dy);
			iEnd =2 * (pno + 1);
			if (iEnd >2)iEnd =2;
			for (i = 0 +2 * pno; i < iEnd; i++)
			{
			Line2D.Double dividingLine = new Line2D.Double(pf.getImageableX(), y,pf.getImageableX() + pf.getImageableWidth(), y);
			g2D.draw(dividingLine);
			y += dy;
			g2D.setFont(new Font("Arial", Font.BOLD, 24));
			g2D.drawString("              "+obj[i].item, (int) pf.getImageableX(), y);
			y += dy;
			g2D.setFont(new Font("Arial", Font.PLAIN, 12));
			g2D.drawString("Location: " + obj[i].location, (int)(pf.getImageableX() + 25), y);
			y += dy;
			if (obj[i].marked)
			g2D.drawString("Item is marked with identifying information.", (int)(pf.getImageableX() + 25), y);
			else
			g2D.drawString("Item is NOT marked with identifying information.", (int)(pf.getImageableX() + 25), y);
			y += dy;
			g2D.drawString("Serial Number: " +obj[i].id, (int) (pf.getImageableX() + 25), y);
			y += dy;
			g2D.drawString("Price: Rs. " + obj[i].price + " and Purchased on: " + obj[i].date, (int) (pf.getImageableX() +25), y);
			y += dy;
			g2D.drawString("Purchased at: " +
			obj[i].store, (int) (pf.getImageableX() + 25), y);
			y += dy;
			g2D.drawString("Note: " + obj[i].note, (int)(pf.getImageableX() + 25), y);
			y += dy;
			try
			{
			Image inventoryImage = new ImageIcon(obj[i].src).getImage ();
			double ratio = (double) (inventoryImage.getWidth(null)) / (double)inventoryImage.getHeight(null);
			g2D.drawImage(inventoryImage, (int) (pf.getImageableX() + 25), y, (int) (100 *ratio), 100, null);
			}
			catch (Exception ex){}
			y += 2 * dy + 100;
			}
			return PAGE_EXISTS;
		}
	}
	JFrame f=new JFrame("Home Inventory Manager ");
	JLabel thing=new JLabel("Inventory Item ");
	JLabel place=new JLabel("Location ");
	JLabel no=new JLabel("Serial Number ");
	JLabel cost=new JLabel("Purchase Price ");
	JLabel time=new JLabel("Date Purchased ");
	JLabel shop=new JLabel("Store/Website ");
	JLabel extra=new JLabel("Note ");
	JLabel image=new JLabel("Photo ");
	JTextField item=new JTextField();
	JTextField id=new JTextField();
	JTextField price=new JTextField();
	JTextField store=new JTextField();
	JTextField note=new JTextField();
	JTextField date=new JTextField();
	JComboBox<String> location;
	JCheckBox marked=new JCheckBox("Marked?",true);
	JTextArea src=new JTextArea();
	JToolBar tool=new JToolBar();
	JButton add=new JButton(new ImageIcon("images\\new.png"));
	JButton delete=new JButton(new ImageIcon("images\\delete.png"));
	JButton save=new JButton(new ImageIcon("images\\save.png"));
	JButton prev=new JButton(new ImageIcon("images\\back.png"));
	JButton next=new JButton(new ImageIcon("images\\next.png"));
	JButton print=new JButton(new ImageIcon("images\\print.png"));
	JButton exit=new JButton(new ImageIcon("images\\exit.png"));
	JButton letters[]=new JButton[26];
	JButton find=new JButton("...");
	JPanel search=new JPanel();
	PhotoPanel frame=new PhotoPanel();
	InventoryItem obj[]=new InventoryItem[6];
	Set<String> set=new HashSet<>();
	Vector<String> a;
	int n,m,j,l;
	HomeInventory(){			
		f.setLayout(new GridBagLayout());	
		GridBagConstraints grid;
		tool.setFloatable(false);
		tool.setBackground(Color.BLUE);
		tool.setOrientation(SwingConstants.VERTICAL);
		tool.addSeparator();
		grid=new GridBagConstraints();
		grid.gridx=0;
		grid.gridy=0;
		grid.gridheight=8;
		grid.fill=GridBagConstraints.VERTICAL;
		f.getContentPane().add(tool,grid);
		Dimension d=new Dimension(70,50);
		add.setText("New");
		add.setToolTipText("Add New Item");
		add.setHorizontalTextPosition(SwingConstants.CENTER);
		add.setVerticalTextPosition(SwingConstants.BOTTOM);
		add.setSize(d);
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add.setEnabled(false);
				delete.setEnabled(true);
				prev.setEnabled(false);
				next.setEnabled(false);
				print.setEnabled(false);
				save.setEnabled(true);
				item.setText("");
				id.setText("");
				date.setText("");
				note.setText("");
				store.setText("");
				src.setText("");
				price.setText("");
				location.setSelectedItem("");
				marked.setSelected(true);
				frame.repaint();
				item.requestFocus();
			}
		});
		delete.setText("Delete");
		delete.setToolTipText("Delete This Item");
		delete.setHorizontalTextPosition(SwingConstants.CENTER);
		delete.setVerticalTextPosition(SwingConstants.BOTTOM);
		delete.setSize(d);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null,"Are you sure you wanna delete this item?","Delete Inventory Item",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION) {
					return;
				}
				if(j!=n) {
					for(int i=j;i<n;i++) {
						obj[i-1]=new InventoryItem();
						obj[i-1]=obj[i];
					}
				}
				n--;
				if(n==0) {
					j=0;
					add.setEnabled(true);
					delete.setEnabled(false);
					prev.setEnabled(false);
					next.setEnabled(false);
					print.setEnabled(false);
					save.setEnabled(false);
					item.setText("");
					id.setText("");
					date.setText("");
					note.setText("");
					store.setText("");
					src.setText("");
					price.setText("");
					location.setSelectedItem("");
					marked.setSelected(true);
					frame.repaint();
					item.requestFocus();
				}
				else {
					j--;
					if(j==0) j=1;
					show(j);
				}
			}
		});
		save.setText("Save");
		save.setToolTipText("Save Item");
		save.setHorizontalTextPosition(SwingConstants.CENTER);
		save.setVerticalTextPosition(SwingConstants.BOTTOM);
		save.setSize(d);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(obj[j-1].item.equals(item.getText().trim())) {
						if(JOptionPane.showConfirmDialog(null,"   You have edited this item\nDo you wanna save the changes?","Save Item",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION) {
							item.requestFocus();
							return;
						}
						obj[j-1].item=item.getText().trim();
						obj[j-1].location=location.getSelectedItem().toString().trim();
						obj[j-1].marked=marked.isSelected();
						obj[j-1].id=id.getText();
						obj[j-1].store=store.getText();
						obj[j-1].price=price.getText();
						obj[j-1].src=src.getText();
						obj[j-1].date=date.getText();
						obj[j-1].note=note.getText();
						show(j);		
						return;
					}
				if(item.getText().trim().equals("")) {
					JOptionPane.showConfirmDialog(null,"No item specified","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
					item.requestFocus();
					return;
				}
				String s=item.getText().trim();
				item.setText(s.substring(0,1).toUpperCase()+s.substring(1));
				try {
				n++;
				j=1;
				if(n!=1) {
					do {
						if(item.getText().compareTo(obj[j-1].item)<0) break;
						j++;
					}while(j<n);
				}
				if(j!=n) {
					for(int i=n;i>=j+1;i--) {
						obj[i-1]=obj[i-2];
						obj[i-2]=new InventoryItem();
					}
				}
				obj[j-1]=new InventoryItem();
				obj[j-1].item=item.getText();
				obj[j-1].location=location.getSelectedItem().toString().trim();
				obj[j-1].marked=marked.isSelected();
				obj[j-1].id=id.getText();
				obj[j-1].store=store.getText();
				obj[j-1].price=price.getText();
				obj[j-1].src=src.getText();
				obj[j-1].date=date.getText();
				obj[j-1].note=note.getText();
				show(j);
				if(n>5) add.setEnabled(false);
				else add.setEnabled(true);
				delete.setEnabled(true);
				print.setEnabled(true);			
				}
				catch(Exception ex) {}
			}
		});		
		prev.setText("Previous");
		prev.setToolTipText("Go Back");
		prev.setHorizontalTextPosition(SwingConstants.CENTER);
		prev.setVerticalTextPosition(SwingConstants.BOTTOM);
		prev.setSize(d);
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j--;
				show(j);
			}
		});		
		next.setText("Next");
		next.setToolTipText("Go To Next Item");
		next.setHorizontalTextPosition(SwingConstants.CENTER);
		next.setVerticalTextPosition(SwingConstants.BOTTOM);
		next.setSize(d);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j++;
				show(j);
			}
		});		
		print.setText("Print");
		print.setToolTipText("Print Inventory List");
		print.setHorizontalTextPosition(SwingConstants.CENTER);
		print.setVerticalTextPosition(SwingConstants.BOTTOM);
		print.setSize(d);
		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l=(int)((1+n)/2);
				PrinterJob p=PrinterJob.getPrinterJob();
				p.setPrintable(new Page());
				if(p.printDialog()) {
				try {
					p.print();
				}
				catch(PrinterException exc){
					JOptionPane.showConfirmDialog(null,"Error ocurred- Home Inventory Items can't be printed.","Print Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
				}
				}
			}
		});	
		exit.setText("Exit");
		exit.setToolTipText("Close Program");
		exit.setHorizontalTextPosition(SwingConstants.CENTER);
		exit.setVerticalTextPosition(SwingConstants.BOTTOM);
		exit.setSize(d);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null,"Any unsaved changes will be lost.\nAre you sure you wanna exit?","Exit Program",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION)
					return;
				try {
					PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter("inventory.txt")));
					pw.println(n);
					if(n!=0) {
						for(int i=0;i<n;i++) {
							pw.println(obj[i].item);
							pw.println(obj[i].location);
							pw.println(obj[i].id);
							pw.println(obj[i].marked);
							pw.println(obj[i].price);
							pw.println(obj[i].date);
							pw.println(obj[i].store);
							pw.println(obj[i].note);
							pw.println(obj[i].src);
						}
					}
					m=set.size();
					pw.println(m);
					if(m!=0) {
						for(String s:set) pw.println(s);
					}
					pw.close();
				}
				catch(Exception ex) {}
				System.exit(0);
			}
		});
		tool.add(add);
		tool.add(next);
		tool.add(prev);
		tool.add(save);
		tool.add(print);
		tool.add(delete);
		tool.add(exit);
		grid=new GridBagConstraints();
		grid.gridx=1;
		grid.gridy=0;
		grid.insets=new Insets(10,10,0,10);
		grid.anchor=GridBagConstraints.EAST;
		f.getContentPane().add(thing,grid);
		item.setPreferredSize(new Dimension(470,25));
		grid=new GridBagConstraints();
		grid.gridx=2;
		grid.gridy=0;
		grid.gridwidth=5;
		grid.insets=new Insets(10,0,0,10);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(item,grid);
		grid=new GridBagConstraints();
		grid.gridx=1;
		grid.gridy=1;
		grid.insets=new Insets(10,10,0,10);
		grid.anchor=GridBagConstraints.EAST;
		f.getContentPane().add(place,grid);
		grid=new GridBagConstraints();
		grid.gridx=5;
		grid.gridy=1;
		grid.insets=new Insets(10,10,0,0);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(marked,grid);
		grid=new GridBagConstraints();
		grid.gridx=1;
		grid.gridy=2;
		grid.insets=new Insets(10,10,0,10);
		grid.anchor=GridBagConstraints.EAST;
		f.getContentPane().add(no,grid);
		grid=new GridBagConstraints();
		grid.gridx=2;
		grid.gridy=2;
		grid.gridwidth=3;
		grid.insets=new Insets(10,0,0,10);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(id,grid);
		id.setPreferredSize(new Dimension(300,25));
		grid=new GridBagConstraints();
		grid.gridx=1;
		grid.gridy=3;
		grid.insets=new Insets(10,10,0,10);
		grid.anchor=GridBagConstraints.EAST;
		f.getContentPane().add(cost,grid);
		grid=new GridBagConstraints();
		grid.gridx=2;
		grid.gridy=3;
		grid.gridwidth=2;
		grid.insets=new Insets(10,0,0,10);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(price,grid);
		price.setPreferredSize(new Dimension(200,25));
		grid=new GridBagConstraints();
		grid.gridx=4;
		grid.gridy=3;
		grid.insets=new Insets(10,10,0,10);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(time,grid);
		grid=new GridBagConstraints();
		grid.gridx=5;
		grid.gridy=3;
		grid.insets=new Insets(10,0,0,10);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(date,grid);
		date.setPreferredSize(new Dimension(145,25));
		date.setToolTipText("Use Format DD/MM/YYYY");
		grid=new GridBagConstraints();
		grid.gridx=1;
		grid.gridy=4;
		grid.insets=new Insets(10,10,0,10);
		grid.anchor=GridBagConstraints.EAST;
		f.getContentPane().add(shop,grid);
		grid=new GridBagConstraints();
		grid.gridx=2;
		grid.gridy=4;
		grid.gridwidth=5;
		grid.insets=new Insets(10,0,0,10);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(store,grid);
		store.setPreferredSize(new Dimension(470,25));
		grid=new GridBagConstraints();
		grid.gridx=1;
		grid.gridy=5;
		grid.insets=new Insets(10,10,0,10);
		grid.anchor=GridBagConstraints.EAST;
		f.getContentPane().add(extra,grid);
		grid=new GridBagConstraints();
		grid.gridx=2;
		grid.gridy=5;
		grid.gridwidth=5;
		grid.insets=new Insets(10,0,0,10);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(note,grid);
		note.setPreferredSize(new Dimension(470,25));
		grid=new GridBagConstraints();
		grid.gridx=1;
		grid.gridy=6;
		grid.insets=new Insets(10,10,0,10);
		grid.anchor=GridBagConstraints.EAST;
		f.getContentPane().add(image,grid);
		grid=new GridBagConstraints();
		grid.gridx=2;
		grid.gridy=6;
		grid.gridwidth=4;
		grid.insets=new Insets(10,0,0,10);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(src,grid);
		grid=new GridBagConstraints();
		src.setPreferredSize(new Dimension(405,45));
		src.setEditable(false);
		src.setLineWrap(true);
		src.setBackground(Color.YELLOW);
		src.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		src.setWrapStyleWord(true);	
		grid.gridx=6;
		grid.gridy=6;
		grid.insets=new Insets(10,-55,0,15);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(find,grid);
		find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser select=new JFileChooser();
				select.setDialogType(JFileChooser.OPEN_DIALOG);
				select.setDialogTitle("Open Photo File");
				FileNameExtensionFilter f=new FileNameExtensionFilter("Photo Files","jpg");
				select.addChoosableFileFilter(f);
				select.setFileFilter(f);
				if(select.showOpenDialog(select)==JFileChooser.APPROVE_OPTION)
					showPic(select.getSelectedFile().toString());
			}
		});
		search.setPreferredSize(new Dimension(250,250));
		search.setBorder(BorderFactory.createTitledBorder("Item Search"));
		search.setLayout(new GridBagLayout());
		grid=new GridBagConstraints();
		grid.gridx=1;
		grid.gridy=7;
		grid.gridwidth=3;
		grid.insets=new Insets(20,0,10,0);
		grid.anchor=GridBagConstraints.CENTER;
		f.getContentPane().add(search,grid);
		int x=0,y=0;
		for(int i=0;i<26;i++) {
			letters[i]=new JButton();
			letters[i].setText(String.valueOf((char)(65+i)));
			letters[i].setPreferredSize(new Dimension(40,35));
			letters[i].setMargin(new Insets(-10,-10,-10,-10));
			letters[i].setFocusable(false);
			letters[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(n==0) return;
					String c=e.getActionCommand();
					for(int i=0;i<n;i++) {
						if(obj[i].item.substring(0,1).equals(c)) {
							j=i+1;
							show(j);
							return;
						}
					}
					JOptionPane.showConfirmDialog(null,"No Inventory Items starting with- "+c+"  ","None Found",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
				}
			});
			grid=new GridBagConstraints();
			grid.gridx=x;
			grid.gridy=y;
			search.add(letters[i],grid);
			x++;
			if(x%6==0) {
				x=0;
				y++;
			}
		}
		frame.setPreferredSize(new Dimension(250,230));
		grid=new GridBagConstraints();
		grid.gridx=4;
		grid.gridy=7;
		grid.gridwidth=3;
		grid.insets=new Insets(20,0,10,20);
		grid.anchor=GridBagConstraints.CENTER;
		f.getContentPane().add(frame,grid);
		add.setFocusable(false);
		delete.setFocusable(false);
		next.setFocusable(false);
		prev.setFocusable(false);
		save.setFocusable(false);
		print.setFocusable(false);
		exit.setFocusable(false);
		search.setFocusable(false);
		marked.setFocusable(false);
		frame.setFocusable(false);
		src.setFocusable(false);
		find.setFocusable(false);
		item.requestFocus();
		id.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				price.requestFocus();
			}
		});
		price.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date.requestFocus();
			}
		});
		date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				store.requestFocus();
			}
		});
		store.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				note.requestFocus();
			}
		});
		note.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				item.requestFocus();
			}
		});
		try {
			BufferedReader br=new BufferedReader(new FileReader("inventory.txt"));
			n=Integer.valueOf(br.readLine()).intValue();
			if(n!=0) {
				for(int i=0;i<n;i++) {
					obj[i]=new InventoryItem();
					obj[i].item=br.readLine();
					obj[i].location=br.readLine().toString().trim();
					obj[i].id=br.readLine();
					obj[i].marked=Boolean.valueOf(br.readLine()).booleanValue();
					obj[i].price=br.readLine();
					obj[i].date=br.readLine();
					obj[i].store=br.readLine();
					obj[i].note=br.readLine();
					obj[i].src=br.readLine();
					j=1;
				}
			}
			m=Integer.valueOf(br.readLine()).intValue();
			if(m!=0) {
				for(int i=0;i<m;i++) {
					String s=br.readLine().toString().trim();
					if(!(s.equals("")||s.equals(null))) set.add(s);
				}
			}
			br.close();
			m=set.size();
		}
		catch(Exception e){
			n=0;
			j=0;
		}
		if(n==0) {
			delete.setEnabled(false);
			next.setEnabled(false);
			prev.setEnabled(false);
			print.setEnabled(false);
		}	
		a=new Vector<>();
		for(String s:set) a.add(s); 
		location=new JComboBox<String>(a);
		grid=new GridBagConstraints();
		grid.gridx=2;
		grid.gridy=1;
		grid.gridwidth=3;
		grid.insets=new Insets(10,0,0,10);
		grid.anchor=GridBagConstraints.WEST;
		f.getContentPane().add(location,grid);
		location.setPreferredSize(new Dimension(300,25));
		location.setEditable(true);
		location.setBackground(Color.WHITE);
		location.setFont(new Font("Arial", Font.PLAIN, 12));
		location.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s=location.getSelectedItem().toString().trim();
				if(s.equals("")||s.equals(null)) return;
				else if(set.contains(s)) id.requestFocus();
				else {
					set.add(s);
					location.removeAllItems();
					for(String i:set) location.addItem(i);
				}
			}
		});
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				location.requestFocus();
			}
		});	
		f.pack();
		Dimension size =Toolkit.getDefaultToolkit().getScreenSize();
		f.setBounds((int) (0.5 * (size.width - f.getWidth())), (int) (0.5 * (size.height
		- f.getHeight())), f.getWidth(),f.getHeight());
		f.setVisible(true);
		f.setResizable(false);
		f.addWindowListener(new CloseAction());
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		show(j);	
	}
	private void show(int j) {
		item.setText(obj[j-1].item);
		location.setSelectedItem(obj[j-1].location);
		marked.setSelected(obj[j-1].marked);
		id.setText(obj[j-1].id);
		price.setText(obj[j-1].price);
		date.setText(obj[j-1].date);
		store.setText(obj[j-1].store);
		note.setText(obj[j-1].note);
		showPic(obj[j-1].src);
		next.setEnabled(true);
		prev.setEnabled(true);
		if(j==1) prev.setEnabled(false);
		if(j==n) next.setEnabled(false);
		if(n>5) add.setEnabled(false);
		else add.setEnabled(true);
		item.requestFocus();
	}
	private void showPic(String s) {
		if(!s.equals("")) {
			try {
				src.setText(s);
			}
			catch(Exception e) {
				src.setText("");
			}
		}
		else src.setText("");
		frame.repaint();
	}
	public static void main(String[] args) {
		new HomeInventory();
	}

}
