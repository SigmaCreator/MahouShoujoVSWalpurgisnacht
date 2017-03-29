package Interface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import Game.*;
import Game.Character;

public class Tabuleiro extends JFrame implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;
	private /*@ spec_public @*/ Character meduka, walpurg;
	private /*@ spec_public @*/ boolean playing = true;
	private /*@ spec_public @*/ JButton playButton, btnStart, btnSave, btnLoad;
	private static /*@ spec_public @*/ int TAMLC = 20;
	private /*@ spec_public @*/ JLabel lbRounds, lbWitches, lbMahou; 
	private /*@ spec_public @*/ JTextField tfRounds, tfWitches, tfMahou;
	private /*@ spec_public @*/ JPanel[][] boardCells = new JPanel[TAMLC][TAMLC];
	private /*@ spec_public @*/ JPanel pnlMain = new JPanel(new GridLayout(TAMLC, TAMLC));
	private /*@ spec_public @*/ ChartPanel chartPanel, chartPanel2;
	private /*@ spec_public @*/ DefaultPieDataset pieDataset;
	private /*@ spec_public @*/ DefaultCategoryDataset barDataset = new DefaultCategoryDataset();;
	private /*@ spec_public @*/ JFreeChart chart, barchart;
	private /*@ spec_public @*/ JTabbedPane tabelas;
	private /*@ spec_public @*/ TextArea context;
	public static void main(String[] args){
		SwingUtilities.invokeLater( new Runnable() { 
			@Override
			public void run(){ 
				new Tabuleiro();
			} 
		});
	}
	
	//@requires e!=null;
	@Override
	public void update(Observable e, Object arg){
		if (e instanceof Field){
			eraseBoard();
			arrangePieces();
			pieDataset.setValue("Mahou Shoujo", Field.getInstance().getQuantityMahou());
			pieDataset.setValue("Witches", Field.getInstance().getQuantityWitch());
			if(meduka==null || walpurg==null){
				meduka = Field.getInstance().getChara(0, 0);
				walpurg = Field.getInstance().getChara(19, 19);
			}
				barDataset.addValue(meduka.getHB(), "Madoka", "Life");
				barDataset.addValue(meduka.getPB(), "Madoka", "PurityBar");
				barDataset.addValue(walpurg.getHB(), "Walpurgisnacht", "Life");
		}
	}
	
	/*@ ensures pnlMain.getBounds()!=null &&
	    pnlMain.getBackground()!=null &&
	    pnlMain.getPreferredSize()!=null &&
	    btnStart != null &&
	    playButton != null &&
	    btnSave !=null &&
	    btnLoad !=null &&
	    lbWitches !=null &&
	    tfWitches !=null &&
	    lbMahou !=null &&
	    tfMahou !=null &&
	  	lbRounds !=null &&
	 	tfRounds !=null &&
	 	context !=null &&
	 	!context.isEditable() &&
	 	tabelas !=null;
	 @*/
	public Tabuleiro(){
		Field faux = Field.getInstance();
		faux.addObserver(this);
		Container c = getContentPane();
		setBackground( new Color (255, 255, 255) );
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Mahou Shoujo x Walpurgisnacht");
		setResizable(false);
		c.setLayout( new BorderLayout() );
		c.setSize(500,500);
		pnlMain.setBounds(3, 3, 500, 500);
		pnlMain.setBackground( new Color(255, 255, 255) );
		pnlMain.setPreferredSize(new Dimension (650, 650));
		
		c.add(pnlMain, BorderLayout.WEST);
		btnStart = new JButton("Comecar");
		btnStart.addActionListener(this);
		
		playButton = new JButton("Avanca");
		playButton.addActionListener(this);
		
		btnSave = new JButton("Salvar Estado Atual");
		btnSave.addActionListener(this);
		
		btnLoad = new JButton("Carregar Estado Salvo");
		btnLoad.addActionListener(this);
		
		lbWitches = new JLabel("Numero inicial de Bruxas: ");
		tfWitches = new JTextField(3);
		
		lbMahou = new JLabel("Numero inicial de Mahou Shoujo: ");
		tfMahou = new JTextField(3);
		
		lbRounds = new JLabel("Numero de rounds:");
		tfRounds = new JTextField(3);
		
		JPanel pnEntrada = new JPanel();
		BoxLayout layout = new BoxLayout(pnEntrada, BoxLayout.Y_AXIS);
		Dimension dim = new Dimension(100, 25);
		pnEntrada.setLayout(layout );
		pnEntrada.add(lbMahou);
		pnEntrada.add(Box.createRigidArea(new Dimension(0,5)));
		pnEntrada.add(tfMahou);
		pnEntrada.add(Box.createRigidArea(new Dimension(0,5)));
		tfMahou.setPreferredSize(dim);
		tfMahou.setMaximumSize(dim);
		tfMahou.setAlignmentX(LEFT_ALIGNMENT);
		pnEntrada.add(lbWitches);
		pnEntrada.add(Box.createRigidArea(new Dimension(0,5)));
		pnEntrada.add(tfWitches);
		pnEntrada.add(Box.createRigidArea(new Dimension(0,5)));
		tfWitches.setPreferredSize(dim);
		tfWitches.setMaximumSize(dim);
		tfWitches.setAlignmentX(LEFT_ALIGNMENT);
		pnEntrada.add(btnStart);
		pnEntrada.add(Box.createRigidArea(new Dimension(0,5)));
		pnEntrada.add(lbRounds);
		pnEntrada.add(Box.createRigidArea(new Dimension(0,5)));
		pnEntrada.add(tfRounds);
		pnEntrada.add(Box.createRigidArea(new Dimension(0,5)));
		tfRounds.setPreferredSize(dim);
		tfRounds.setMaximumSize(dim);
		tfRounds.setAlignmentX(LEFT_ALIGNMENT);
		pnEntrada.add(playButton);
		pnEntrada.add(Box.createRigidArea(new Dimension(0,5)));
		pnEntrada.add(btnSave);
		pnEntrada.add(Box.createRigidArea(new Dimension(0,5)));
		pnEntrada.add(btnLoad);
		pnEntrada.add(Box.createRigidArea(new Dimension(0,5)));
		c.add(pnEntrada, BorderLayout.EAST);
		
		JPanel pnContext = new JPanel();
		context = new TextArea(readContext(), 20, 60);
		context.setEditable(false);
		pnContext.add(context);
		tabelas = new JTabbedPane();
		generatePieChart();
		generateBarChart();
		tabelas.addTab("Contexto", pnContext);
		tabelas.addTab("Relacao de Personagens", chartPanel);
		tabelas.addTab("Madoka x Walpurgisnacht", chartPanel2);
		pnEntrada.add(tabelas);
		this.drawBoard();
		pack();
		setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	
	/*@ public normal_behavior
		ensures \result !=null;
		
		also
		public exceptional_behavior
		signals(IOException);
	@*/
	private /*@ spec_public @*/ String readContext(){
		String text = "";
		try{
			FileReader arq = new FileReader(System.getProperty("user.dir")+"/context/context.txt");
	    	BufferedReader br = new BufferedReader(arq);
	    	
	    	String linha;
	    	while((linha = br.readLine())!=null){
	    		text+=linha+"\n";
	    	}
	    	
	    	br.close();
	    	arq.close();
		}catch(IOException e){
			JOptionPane.showMessageDialog(this, "Erro ao carregar contexto");
		}
		return text;
	}
	
	//@ensures barchart !=null && chartPanel2 != null;
	private /*@ spec_public @*/ void generateBarChart(){
		barchart  = ChartFactory.createBarChart("Madoka x Walpurgisnacht",
				"Life/PurityBar",
				"Pontos",
				barDataset, 
				PlotOrientation.VERTICAL, 
				true, true, false);
		chartPanel2 = new ChartPanel(barchart);
        chartPanel2.setPreferredSize(new Dimension(500, 270));
	}
	
	//@ensures \result != null;
	 private /*@ spec_public @*/ DefaultPieDataset createPieDataset() {
	        DefaultPieDataset result = new DefaultPieDataset();
	        result.setValue("Mahou Shoujo", Field.getInstance().getQuantityMahou());
	        result.setValue("Witches", Field.getInstance().getQuantityWitch());
	        return result;
	        
	 }
	 
	 //@ensures \result == chart && chart !=null;
    private /*@ spec_public @*/ JFreeChart createPieChart(String title) {
        
        chart = ChartFactory.createPieChart3D(title,     
            pieDataset,               
            true,                   
            true,
            false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setDataset(pieDataset);
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
    
    //@ensures pieDataset!=null && chart!=null && chartPanel !=null;
	private /*@ spec_public @*/  void generatePieChart(){
        pieDataset = createPieDataset();
        chart = createPieChart( "Population");
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
	}
	
	/*@ public normal_behavior
	  	ensures playing==true <== e.getActionCommand().equals("Recomecar");
	  	
	  	public exceptional_behavior
	  	signals(NumberFormatException);
	  	signals(GameOverException);
	 @*/
	@Override
	public void actionPerformed(ActionEvent e){
		try{
		String action = e.getActionCommand();
		if(action.equals("Comecar")){
			Field.getInstance().generateSimStart(Integer.parseInt(tfWitches.getText()), Integer.parseInt(tfMahou.getText()));
			((JButton)e.getSource()).setText("Recomecar");
			
		}else if(action.equals("Recomecar")){
			playing = true;
			Field.getInstance().restartSimulation(Integer.parseInt(tfWitches.getText()), Integer.parseInt(tfMahou.getText()));
		}else if(action.equals("Avanca") && playing){
			Field.getInstance().forwardSimulation(Integer.parseInt(tfRounds.getText()));
		}else if(action.equals("Salvar Estado Atual")){
			String filename = JOptionPane.showInputDialog("Insira o nome para o arquivo, sem extensoes");
			if(!Field.getInstance().saveSimulation(filename))JOptionPane.showMessageDialog(this, "Erro ao Salvar simulacao");
			else JOptionPane.showMessageDialog(this, "Simulacao salva com sucesso");
		}else if(action.equals("Carregar Estado Salvo")){
			File f = fileChooser();
			boolean loaded = Field.getInstance().loadSimulation(f);
			if(!loaded) JOptionPane.showMessageDialog(this, "Erro ao carregar simulacao");
			
		}
		}catch(NumberFormatException nf){
			JOptionPane.showMessageDialog(this, "Entrada invalida.");
		}catch(GameOverException go){
			playing = false;
			update(Field.getInstance(), null);
			meduka = null;
			walpurg = null;
			JOptionPane.showMessageDialog(this, go.getMessage());
			//TODO cool things
		}	
	}
	
	//@ ensures (\forall int i,j; 0<=i && i<TAMLC && 0<=j && j<TAMLC; boardCells[i][j].getBackground()!=null);
	private /*@ spec_public @*/ void drawBoard() {
		for (int y = 0; y < TAMLC; y++){
			for (int x = 0; x < TAMLC; x++) {
				boardCells[y][x] = new JPanel(new BorderLayout());
				pnlMain.add(boardCells[y][x]);
				if (y % 2 == 0)
					if (x % 2 != 0)
						boardCells[y][x].setBackground(Color.BLACK);
					else
						boardCells[y][x].setBackground(Color.PINK);
				else if (x % 2 == 0)
					boardCells[y][x].setBackground(Color.BLACK);
				else
					boardCells[y][x].setBackground(Color.PINK);
			}
		}
	}
	
	/*@ ensures (\forall int i,j, z; 0<=i && i<TAMLC && 0<=j && j<TAMLC && 0<=z && z<TAMLC;
	    		boardCells[i][j].getComponent(z).getClass().toString().indexOf("JLabel")<=-1);
	@*/
	private /*@ spec_public @*/ void eraseBoard(){
		for(int i=0; i<TAMLC;i++){
			for(int j=0; j<TAMLC; j++){
				for (int z = 0; z <boardCells[i][j].getComponentCount(); z++){
			    	 if (boardCells[i][j].getComponent(z).getClass().toString().indexOf("JLabel") > -1){
			    		 boardCells[i][j].remove(z);
			             boardCells[i][j].repaint();   
			    	 }
			     }
			}
		}
	}
	
	//@ensures (\forall int y, x; y>=0 && y<TAMLC && x>=0 && x<TAMLC; boardCells[y][x].getComponentCount()>0);
	private /*@ spec_public @*/ void arrangePieces() {
		for (int y = 0; y < TAMLC; y++){
			for (int x = 0; x < TAMLC; x++) {
				if (Field.getInstance().getChara(y, x) != null) {
					JLabel lbChar = getCharacterObject(Field.getInstance().getChara(y, x));
					boardCells[y][x].add(lbChar, BorderLayout.CENTER);
					boardCells[y][x].validate();
				}
			}
		}
	}
	
	// @ requires chara != null;
	// @ ensures \result !=null;
	private /*@ spec_public @*/ JLabel getCharacterObject(Character chara) {
		switch(chara.getName()){
		case "Madoka":
			return new JLabel(this.madoka);
		case "Kyoko":
			return new JLabel(this.kyoko);
		case "Homura":
			return new JLabel(this.homura);
		case "Mami":
			return new JLabel(this.mami);
		case "Bebe":
			return new JLabel(this.bebe);
		case "BebeWitch":
			return new JLabel(this.bwitch);
		case "Sayaka":
			return new JLabel(this.sayaka);
		case "SayakaWitch":
			return new JLabel(this.sawitch);
		case "Walpurgisnacht":
			return new JLabel(this.walpurgis);
		default:
			return new JLabel(this.witch);
		}
	}
	
	public File fileChooser(){
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")+"/saves/");
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT file", "txt");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	      return chooser.getSelectedFile();
	    }else return null;
	}

	private /*@ spec_public @*/ ImageIcon madoka = new ImageIcon(System.getProperty("user.dir")+"/images/madoka.gif");
	private /*@ spec_public @*/ ImageIcon kyoko = new ImageIcon(System.getProperty("user.dir")+"/images/kyoko.gif");
	private /*@ spec_public @*/ ImageIcon mami = new ImageIcon(System.getProperty("user.dir")+"/images/mami.gif");
	private /*@ spec_public @*/ ImageIcon bebe = new ImageIcon(System.getProperty("user.dir")+"/images/bebe.gif");
	private /*@ spec_public @*/ ImageIcon sayaka = new ImageIcon(System.getProperty("user.dir")+"/images/sayaka.gif");
	private /*@ spec_public @*/ ImageIcon homura = new ImageIcon(System.getProperty("user.dir")+"/images/homura.gif");
	private /*@ spec_public @*/ ImageIcon bwitch = new ImageIcon(System.getProperty("user.dir")+"/images/bwitch.gif");
	private /*@ spec_public @*/ ImageIcon sawitch = new ImageIcon(System.getProperty("user.dir")+"/images/switch.gif");
	private /*@ spec_public @*/ ImageIcon witch = new ImageIcon(System.getProperty("user.dir")+"/images/witch.gif");
	private /*@ spec_public @*/ ImageIcon walpurgis = new ImageIcon(System.getProperty("user.dir")+"/images/walpurgis.gif");
}
