/**
 * Title:        SKMainFrame
 * Description:  MainFrame for Sketcher
 * @version      1.00.04b
 */
/*Copyright (C) June 22, 2001 Meera Sitharam

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
http://www.gnu.org/copyleft/gpl.html

You should have received a copy of the GNU General Public License
in the documentation index in the documentation
folder of the FRONTIER-gnu directory; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

   package sketcher;

   import javax.swing.*;
   import javax.swing.table.*;
   import javax.swing.tree.*;
   import javax.swing.event.*;
   import javax.swing.border.*;
   import java.awt.*;
   import java.awt.event.*;
   import javax.swing.DefaultCellEditor;
   import java.io.*;
   import java.util.zip.ZipFile;
   import java.util.zip.ZipEntry;
   import java.util.zip.ZipInputStream;
   import java.util.zip.ZipOutputStream;
   import java.util.Enumeration;
   import utuJava;

   public class SKMainFrame extends JFrame {
      public  String      HomeDir;
      public  boolean     editingShape; //When true, the property editor is currently showing SKShape data
      public  boolean     drawConstrDetails = true;
      public  boolean     drawGroupRects = false;
      public  boolean     allSelNeedsResolved = false;
      public  boolean     dragged = false;
      public  boolean     b2=false;
      public  boolean     b1=false;
      public  boolean     update=false, optionsUsed=false;
      public  int         fixed = 0, treeID = 1, lastHorizScroll = 0, lastVertScroll = 0, lastHorizScroll1 = 0, lastVertScroll1 = 0, paracount=0, mode=2;
      public  int         IntArray[];
      public  double		  DblArray[];
      public  double[]    consArray = new double[50];
      public  float       lastScale = 1;
   
      public  SKBaseConstraint  SelectedConstraint;
      public  SKBaseConstraint  deleteConstraint;
   
      public  SKConstraintArray vCurrentConstraints = new SKConstraintArray(2);  //Lists all constraints of selected shape
      public  SKConstraintArray allConstraints = new SKConstraintArray(100);
      public  SKConstraintArray oldConstraints = new SKConstraintArray(100);
      public  SKConstraintArray selectedConstraints = new SKConstraintArray(10);
   
      public  SKPropArray       vCurrentProps = new SKPropArray(5); //Contains current shape(s) properties
   
      public  SKPropFactory     propFactory = new SKPropFactory(); //Used to get/free props
   
      public  SKItemInterface   mouseOverItem;
   
      public  SKItemArray       DrawnItems = new SKItemArray(5); //Keeps references to all special-drawn selectable shapes & constraints
   
      public  MRUManager        mruManager;
   
      public  SKGroupTreeNode   groupTree = new SKGroupTreeNode(null,-1,"Groups");
      public  SKGroupTreeNode   newGroupTree = null;
   
      public  SKGTNArray        selectedGroups = new SKGTNArray(3);
      public  SKGTNArray        allGroups = new SKGTNArray(100);
      public  SKGTNArray		  oldGroups = new SKGTNArray(100);
      public  SKGTNArray        groupArray = new SKGTNArray(100);
      public  SKGTNArray		  dagMain = new SKGTNArray(100);
   
      public  SKShapeArray      SelectedShapes = new SKShapeArray(100);  //The currently selected shape(s)
      public  SKShapeArray      selectedGroupShapes = new SKShapeArray(6);
      public  SKShapeArray		  solvingShapes = new SKShapeArray(4);
      public  SKShapeArray      allshapes = new SKShapeArray(100);
      public  SKShapeArray      solvedShapes = new SKShapeArray(100);
      public  SKShapeArray		  oldShapes = new SKShapeArray(100);
   
      public  String[]           scaleValues = new String[] {"200","100","50"};
   
      public  JMenuBar jMenuBar1 = new JMenuBar();
   
      public  JMenu mniFile = new JMenu();
      public  JMenu mniEdit = new JMenu();
      public  JMenu mniHelp = new JMenu();
      public  JMenu mniNewConstraint = new JMenu();
      public  JMenu mniView = new JMenu();
      public  JMenu mniReopen = new JMenu();
      public  JMenu mniRepository = new JMenu();
      public  JMenu mniDesign = new JMenu();
      public  JMenu mniSolveSketch = new JMenu();
      public  JMenu mniSolveOptions = new JMenu();
      public  JMenu mniOptions = new JMenu();
   
      public  JMenuItem mniTangentConstraint = new JMenuItem();
      public  JMenuItem mniAngleConstr = new JMenuItem();
      public  JMenuItem mniNew = new JMenuItem();
      public  JMenuItem mniOpen = new JMenuItem();
      public  JMenuItem mniExit = new JMenuItem();
      public  JMenuItem mniSave = new JMenuItem();
      public  JMenuItem mniSaveAs = new JMenuItem();
      public  JMenuItem mniAbout = new JMenuItem();
      public  JMenuItem mniDelete = new JMenuItem();
      public  JMenuItem mniCopy = new JMenuItem();
      public  JMenuItem mniPaste = new JMenuItem();
      public  JMenuItem mniEditDelete = new JMenuItem();
      public  JMenuItem mniSelectAll = new JMenuItem();
      public  JMenuItem mniMakeGroup = new JMenuItem();    
      public  JMenuItem mniFixGroup = new JMenuItem();
      public  JMenuItem mniUnFixGroup = new JMenuItem();
      public  JMenuItem mniMakeGrouptree = new JMenuItem();
      public  JMenuItem mniCut = new JMenuItem();
      public  JMenuItem mniPopSelectAll = new JMenuItem();
      public  JMenuItem mniPopPaste = new JMenuItem();
      public  JMenuItem mniPopCopy = new JMenuItem();
      public  JMenuItem mniPopCut = new JMenuItem();
      public  JMenuItem mniPopMakeGroup = new JMenuItem();
      public  JMenuItem mniPopFixArcRadius = new JMenuItem();
      public  JMenuItem mniPopFixArcAngle = new JMenuItem();
      public  JMenuItem mniPopFixGroup = new JMenuItem();
      public  JMenuItem mniPopUnFixGroup = new JMenuItem();
      public  JMenuItem mniPopMakeGrouptree = new JMenuItem();
      public  JMenuItem mniPref = new JMenuItem();
      public  JMenuItem mniDistanceConstr = new JMenuItem();
      public  JMenuItem mniIncidenceConstr = new JMenuItem();
      public  JMenuItem mniPerpConstraint = new JMenuItem();
      public  JMenuItem mniParallelConstraint = new JMenuItem();
      public  JMenuItem mniSolve = new JMenuItem();
      public  JMenuItem mniReStart = new JMenuItem();
      public  JMenuItem mniNewTree = new JMenuItem();
      public  JMenuItem mniNewLibrary = new JMenuItem();
      public  JMenuItem mniTreeDelete = new JMenuItem();
      public  JMenuItem mniTest = new JMenuItem();
      public  JMenuItem mniAutoSolve = new JMenuItem();
      public  JMenuItem mniChangeCons = new JMenuItem();
      public  JMenuItem mniAddCons = new JMenuItem();
      public  JMenuItem mniRemoveCons = new JMenuItem();
      public  JMenuItem mniAddTree = new JMenuItem();
      public  JMenuItem mniAddShapeCon = new JMenuItem();
   
      public  JSmallButton btnNew = new JSmallButton();
      public  JSmallButton btnOpen = new JSmallButton();
      public  JSmallButton btnSave = new JSmallButton();
      public  JSmallButton btnSaveAs = new JSmallButton();
      public  JSmallButton btnExit = new JSmallButton();
      public  JSmallButton btnSolve = new JSmallButton();
      public  JSmallButton btnNewTree = new JSmallButton();
   
      public  JToggleButton btnCursor = new JToggleButton();
      public  JToggleButton btnPoint = new JToggleButton();
      public  JToggleButton btnLine = new JToggleButton();
      public  JToggleButton btnCircle = new JToggleButton();
      public  JToggleButton btnArc = new JToggleButton();
      public  JToggleButton btnImage = new JToggleButton();
      public  JToggleButton btnAngleConstraint = new JToggleButton();
      public  JToggleButton btnDistanceConstraint = new JToggleButton();
      public  JToggleButton btnTangentConstraint = new JToggleButton();
      public  JToggleButton btnPerpConstraint = new JToggleButton();
      public  JToggleButton btnParallelConstraint = new JToggleButton();
      public  JToggleButton btnIncidenceConstraint = new JToggleButton();
   
      public  JToolBar toolbarMain = new JToolBar();
      public  JToolBar toolbarEditor = new JToolBar();
      public  JToolBar toolbarStatus = new JToolBar();
      public  JToolBar toolbarShapes = new JToolBar();
      public  JToolBar toolbarConstraints = new JToolBar();
   
      public  JTabbedPane tabpaneObjectProp = new JTabbedPane();
      public  JTabbedPane tabpaneEditor = new JTabbedPane();
      public  JTabbedPane tabPartialSketch = new JTabbedPane();
   
      public  JPanel panelEditor = new JPanel();
      public  JPanel mainFrame = new JPanel();
      public  JPanel panelLeft = new JPanel();
      public  JPanel panelObjects = new JPanel();
      public  JPanel partialSketch = new JPanel();
      public  JPanel panelGroups = new JPanel();
   
      public  BorderLayout borderLayout1 = new BorderLayout();
      public  BorderLayout borderLayout2 = new BorderLayout();
      public  BorderLayout borderLayout3 = new BorderLayout();
      public  BorderLayout borderLayout4 = new BorderLayout();
      public  BorderLayout borderLayout5 = new BorderLayout();
   
      public  JSelectionPanel panelShapeArea = new JSelectionPanel(this);
      public  JSelectionPanel panelPartial = new JSelectionPanel(this);
   
      public  JPopupMenu popupShape = new JPopupMenu();
      public  JPopupMenu popupGroup = new JPopupMenu();
      public  JPopupMenu popTree = new JPopupMenu();
   
      public  JStatusBar sbStatus = new JStatusBar();
   
      public  Border border1;
   
      public  JComboBox cmbShapes = new JComboBox();
      public  JComboBox cmbScale = new JComboBox();
   
      public  JTable tableObjectProp = new JTable();
   
      public  JTree treeConstraints = new JTree();
      public  JTree treeGroups = new JTree();
   
      public  JList lstGroupShapes = new JList();
   
      public  JCheckBoxMenuItem mniDrawConstraints = new JCheckBoxMenuItem();
      public  JCheckBoxMenuItem mniRepositoryViewer = new JCheckBoxMenuItem();
   
      public static IndexedButtonGroup bgShapes = new IndexedButtonGroup();
   
      public  SKRepository repository;
   
      public  JSplitPane jSplitPane1 = new JSplitPane();
   
      public  JScrollBar scrHoriz = new JScrollBar();
      public  JScrollBar scrVert = new JScrollBar();
      public  JScrollBar scrHoriz1 = new JScrollBar();
      public  JScrollBar scrVert1 = new JScrollBar();
   
      public  JLabel lblScale = new JLabel();
   
      private String      		sFileName;  //File and path to currently edited data -- Blank when new (never saved)
      private SKShapeArray    ClipShapes = new SKShapeArray(0);  //This is our "clipboard"
      private int         		Updating;  //Used to prevent redundant calls to updateShapeData() -- mainly from setting cmbShapes.setItemIndex()
      public int         		IDCnt=1,oldShapeID;  //This is incremented everytime a shape is created to give unique IDs
      public int         		ConstrIDCnt, oldConstraintID;  //This is incremented everytime a constraint is created to give unique IDs
      public int         		GroupIDCnt,oldGroupID;  //This is incremented everytime a group is created to give unique IDs
      private int         		SelectedPropLevel;  //indicates the PropTypes that are currently visible
      private boolean     		ClipWasCut;  //true if items were cut, false if copied
      private boolean     		bDataModified = false;  //true if user has modified data since last save
      private static boolean 			saveArrays = false; //save additional data?
      private static boolean			dataExists = false; //additional data present?
   
      public static void main(String[] args)
      {
      //Set Look and Feel
         try
         {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
         }
            catch (Exception e)
            {
            }
      
         File f = new File("");
         String st = f.getAbsolutePath();
      
      //Create the top-level container and add contents to it.
         SKMainFrame frameMain = new SKMainFrame(st);
      
         SKOptions.loadOptions(st+SKOptions.optionFileName);
         frameMain.setVisible(true);
      
      }
   
      public SKMainFrame(String Home)
      {
         HomeDir = Home;
      
         try
         {
            jbInit();
            setLocation(0,0);
            setSize(1120,820);
         
         //Center on desktop
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension d = getSize();
            int x = (screen.width - d.width) >> 1;
            int y = (screen.height - d.height) >> 1;
            setLocation(x, y);
         }
            catch(Exception e)
            {
               e.printStackTrace();
            }
      }
   
      private void jbInit() throws Exception
      {
         border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(142, 142, 142),new Color(99, 99, 99));
         this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
         this.setJMenuBar(jMenuBar1);
         this.setTitle("Sketcher");
         this.addWindowListener(
                                 new java.awt.event.WindowAdapter()
                                 {
                                 
                                    public void windowClosing(WindowEvent e)
                                    {
                                       this_windowClosing(e);
                                    }
                                 });
      
         mniFile.setText("File");
         mniNew.setPreferredSize(new Dimension(70, 21));
         mniNew.setIcon(new ImageIcon(HomeDir+"/images/new.gif"));
         mniNew.setText("New");
         mniNew.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniNew_actionPerformed(e);
                                    }
                                 });
         mniSolveSketch.setText("Solve");
      
         mniOpen.setIcon(new ImageIcon(HomeDir+"/images/open.gif"));
         mniOpen.setText("Open");
         mniOpen.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniOpen_actionPerformed(e);
                                    }
                                 });
         mniExit.setIcon(new ImageIcon(HomeDir+"/images/exit.gif"));
         mniExit.setText("Exit");
         mniExit.addActionListener(
                                 new java.awt.event.ActionListener() {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniExit_actionPerformed(e);
                                    }
                                 });
         btnNew.setMaximumSize(new Dimension(24, 24));
         btnNew.setPreferredSize(new Dimension(24, 24));
         btnNew.setToolTipText("Creates a new project");
         btnNew.setIcon(new ImageIcon(HomeDir+"/images/new.gif"));
         btnNew.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniNew_actionPerformed(e);
                                    }
                                 });
         btnOpen.setMaximumSize(new Dimension(24, 24));
         btnOpen.setPreferredSize(new Dimension(24, 24));
         btnOpen.setToolTipText("Open a project");
         btnOpen.setIcon(new ImageIcon(HomeDir+"/images/open.gif"));
         btnOpen.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniOpen_actionPerformed(e);
                                    }
                                 });
         mniSave.setIcon(new ImageIcon(HomeDir+"/images/save.gif"));
         mniSave.setText("Save");
         mniSave.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniSave_actionPerformed(e);
                                    }
                                 });
         mniSaveAs.setIcon(new ImageIcon(HomeDir+"/images/saveas.gif"));
         mniSaveAs.setText("Save as...");
         mniSaveAs.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniSaveAs_actionPerformed(e);
                                    }
                                 });
         btnExit.setMaximumSize(new Dimension(24, 24));
         btnExit.setPreferredSize(new Dimension(24, 24));
         btnExit.setToolTipText("Exit Sketcher");
         btnExit.setIcon(new ImageIcon(HomeDir+"/images/exit.gif"));
         btnExit.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniExit_actionPerformed(e);
                                    }
                                 });
         btnSolve.setMaximumSize(new Dimension(24, 24));
         btnSolve.setPreferredSize(new Dimension(24, 24));
         btnSolve.setToolTipText("Solve with Maple");
         btnSolve.setIcon(new ImageIcon(HomeDir+"/images/exit.gif"));
         btnSolve.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniSolve_actionPerformed(e);
                                    }
                                 });
         btnSolve.setMaximumSize(new Dimension(24, 24));
         btnNewTree.setPreferredSize(new Dimension(24, 24));
         btnNewTree.setToolTipText("Make New Tree");
         //btnNewTree.setIcon(new ImageIcon(HomeDir+"/images/exit.gif"));
         btnNewTree.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniNewTree_actionPerformed(e);
                                    }
                                 });
      
         btnSave.setMaximumSize(new Dimension(24, 24));
         btnSave.setPreferredSize(new Dimension(24, 24));
         btnSave.setToolTipText("Save current project");
         btnSave.setIcon(new ImageIcon(HomeDir+"/images/save.gif"));
         btnSave.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniSave_actionPerformed(e);
                                    }
                                 });
         btnSaveAs.setMaximumSize(new Dimension(24, 24));
         btnSaveAs.setPreferredSize(new Dimension(24, 24));
         btnSaveAs.setToolTipText("Save current project as another file");
         btnSaveAs.setIcon(new ImageIcon(HomeDir+"/images/saveas.gif"));
         btnSaveAs.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniSaveAs_actionPerformed(e);
                                    }
                                 });
         toolbarEditor.setOrientation(JToolBar.VERTICAL);
         mniEdit.setText("Edit");
         mniEdit.addMenuListener(
                                 new javax.swing.event.MenuListener()
                                 {
                                 
                                    public void menuCanceled(MenuEvent e)
                                    {
                                    }
                                 
                                    public void menuDeselected(MenuEvent e)
                                    {
                                    }
                                 
                                    public void menuSelected(MenuEvent e)
                                    {
                                       mniEdit_menuSelected(e);
                                    }
                                 });
         mniHelp.setText("Help");
         mniAbout.setText("About");
         mniAbout.setIcon(new ImageIcon(HomeDir+"/images/about.gif"));
         panelShapeArea.addMouseListener(
                                 new java.awt.event.MouseAdapter()
                                 {
                                 
                                    public void mouseClicked(MouseEvent e)
                                    {
                                       panelShapeArea_mouseClicked(e);
                                    }
                                 
                                    public void mouseReleased(MouseEvent e)
                                    {
                                       panelShapeArea_mouseReleased(e);
                                    }
                                 });
         panelGroups.addMouseListener(
                                 new java.awt.event.MouseAdapter()
                                 {
                                    public void mouseClicked(MouseEvent e)
                                    {
                                       panelGroups_mouseClicked(e);
                                    }
                                 
                                 
                                    public void mouseReleased(MouseEvent e)
                                    {
                                       panelGroups_mouseReleased(e);
                                    }
                                 });
         mniDelete.setText("Delete");
         mniDelete.setIcon(new ImageIcon(HomeDir+"/images/delete.gif"));
         mniDelete.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniDelete_actionPerformed(e);
                                    }
                                 });
         toolbarStatus.setBorder(border1);
         toolbarStatus.setFloatable(false);
         panelShapeArea.addMouseMotionListener(
                                 new java.awt.event.MouseMotionAdapter()
                                 {
                                 
                                    public void mouseDragged(MouseEvent e)
                                    {
                                       panelShapeArea_mouseDragged(e);
                                    }
                                 });
         mniSelectAll.setText("Select All");
         mniSelectAll.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniSelectAll_actionPerformed(e);
                                    }
                                 });
         sbStatus.setPreferredSize(new Dimension(20, 15));
         mniCut.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniCut_actionPerformed(e);
                                    }
                                 });
         mniCopy.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniCopy_actionPerformed(e);
                                    }
                                 });
         mniPaste.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniPaste_actionPerformed(e);
                                    }
                                 });
         mniPopCut.setText("Cut");
         mniPopCut.setIcon(new ImageIcon(HomeDir+"/images/cut.gif"));
         mniPopCut.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniCut_actionPerformed(e);
                                    }
                                 });
         mniPopCopy.setText("Copy");
         mniPopCopy.setIcon(new ImageIcon(HomeDir+"/images/copy.gif"));
         mniPopCopy.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniCopy_actionPerformed(e);
                                    }
                                 });
         mniPopPaste.setText("Paste");
         mniPopPaste.setIcon(new ImageIcon(HomeDir+"/images/paste.gif"));
         mniPopPaste.addActionListener(
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniPaste_actionPerformed(e);
                                    }
                                 });
         mniPopSelectAll.setText("Select All");
         mniPopSelectAll.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniSelectAll_actionPerformed(e);
                                    }
                                 });
         popupShape.addPopupMenuListener(
                                 new javax.swing.event.PopupMenuListener()
                                 {
                                 
                                    public void popupMenuCanceled(PopupMenuEvent e)
                                    {
                                    }
                                 
                                    public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
                                    {
                                    }
                                 
                                    public void popupMenuWillBecomeVisible(PopupMenuEvent e)
                                    {
                                       popupShape_popupMenuWillBecomeVisible(e);
                                    }
                                 });
         popupGroup.addPopupMenuListener(
                              
                                 new javax.swing.event.PopupMenuListener()
                                 
                                 {
                                 
                                 
                                    public void popupMenuCanceled(PopupMenuEvent e)
                                    
                                    {
                                    }
                                 
                                 
                                    public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
                                    
                                    {
                                    }
                                 
                                 
                                    public void popupMenuWillBecomeVisible(PopupMenuEvent e)
                                    
                                    {
                                       popupGroup_popupMenuWillBecomeVisible(e);
                                    }
                                 });
      
         mniPref.setText("Preferences");
         mniPref.setIcon(new ImageIcon(HomeDir+"/images/props.gif"));
         mniPref.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniPref_actionPerformed(e);
                                    }
                                 });
         mniNewConstraint.setText("New Constraint");
         mniDistanceConstr.setText("Distance");
         mniDistanceConstr.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniDistanceConstr_actionPerformed(e);
                                    }
                                 });
         mniView.setText("View");
         mniDrawConstraints.setText("Draw Constraints");
         mniDrawConstraints.setState(true);
         mniDrawConstraints.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       chkDrawConstraints_actionPerformed(e);
                                    }
                                 });
         panelShapeArea.addMouseMotionListener(
                                 new java.awt.event.MouseMotionAdapter()
                                 {
                                 
                                    public void mouseMoved(MouseEvent e)
                                    {
                                       panelShapeArea_mouseMoved(e);
                                    }
                                 });
         treeConstraints.addMouseListener(
                                 new java.awt.event.MouseAdapter()
                                 {
                                 
                                    public void mouseClicked(MouseEvent e)
                                    {
                                       treeConstraints_mouseClicked(e);
                                    }
                                 
                                    public void mouseReleased(MouseEvent e)
                                    {
                                       treeConstraints_mouseReleased(e);
                                    }
                                 });
         mniIncidenceConstr.setText("Incidence");
         mniIncidenceConstr.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniIncidenceConstr_actionPerformed(e);
                                    }
                                 });
         mniPerpConstraint.setText("Perpendicular");
         mniPerpConstraint.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniPerpConstraint_actionPerformed(e);
                                    }
                                 });
         mniParallelConstraint.setText("Parallel");
         mniParallelConstraint.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniParallelConstraint_actionPerformed(e);
                                    }
                                 });
         mniRepositoryViewer.setText("Repository Viewer");
         mniRepositoryViewer.setState(false);
         mniRepositoryViewer.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniRepositoryViewer_actionPerformed(e);
                                    }
                                 });
         mniRepository.setText("Repository");
         mniRepository.addMenuListener(
                                 new javax.swing.event.MenuListener()
                                 {
                                 
                                    public void menuCanceled(MenuEvent e)
                                    {
                                    }
                                 
                                    public void menuDeselected(MenuEvent e)
                                    {
                                    }
                                 
                                    public void menuSelected(MenuEvent e)
                                    {
                                       mniRepository_menuSelected(e);
                                    }
                                 });
         mniNewLibrary.setText("Create New Object Library");
         mniDesign.setText("Design");
         mniDesign.addMenuListener(
                              
                                 new javax.swing.event.MenuListener()
                                 
                                 {
                                 
                                 
                                    public void menuCanceled(MenuEvent e)
                                    
                                    {
                                    }
                                 
                                 
                                    public void menuDeselected(MenuEvent e)
                                    
                                    {
                                    }
                                 
                                 
                                    public void menuSelected(MenuEvent e)
                                    
                                    {
                                       mniDesign_menuSelected(e);
                                    }
                                 });
         mniSolve.setText("Solve using maple");
         mniSolve.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniSolve_actionPerformed(e);
                                    }
                                 });
         mniSolveOptions.setText("Solve");
         mniNewTree.setText("Make new tree");
         mniNewTree.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniNewTree_actionPerformed(e);
                                    }
                                 });
      
      
         treeConstraints.addKeyListener(
                                 new java.awt.event.KeyAdapter()
                                 {
                                 
                                    public void keyTyped(KeyEvent e)
                                    {
                                       treeConstraints_keyTyped(e);
                                    }
                                 });
         mniTreeDelete.setText("Delete");
         mniTreeDelete.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniTreeDelete_actionPerformed(e);
                                    }
                                 });
         mniAngleConstr.setText("Angle");
         mniAngleConstr.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniAngleConstr_actionPerformed(e);
                                    }
                                 });
         mniReopen.setText("Reopen");
         mniReopen.setIcon(new ImageIcon(HomeDir+"/images/open.gif"));
         mniTangentConstraint.setText("Tangent");
         mniTangentConstraint.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniTangentConstraint_actionPerformed(e);
                                    }
                                 });
         btnCircle.setMaximumSize(new Dimension(30, 27));
         btnCircle.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       ShapeButton_actionPerformed(e);
                                    }
                                 });
         btnCircle.setPreferredSize(new Dimension(30, 27));
         btnCircle.setToolTipText("Circle");
         btnCircle.setIcon(new ImageIcon(HomeDir+"/images/circle.gif"));
         btnArc.setMaximumSize(new Dimension(30, 27));
         btnArc.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       ShapeButton_actionPerformed(e);
                                    }
                                 });
         btnArc.setPreferredSize(new Dimension(30, 27));
         btnArc.setToolTipText("Arc");
         btnArc.setIcon(new ImageIcon(HomeDir+"/images/arc.gif"));
         btnPoint.setIcon(new ImageIcon(HomeDir+"/images/point.gif"));
         btnPoint.setToolTipText("Point");
         btnPoint.setPreferredSize(new Dimension(30, 27));
         btnPoint.setMaximumSize(new Dimension(30, 27));
         btnPoint.setMinimumSize(new Dimension(30, 27));
         btnPoint.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       ShapeButton_actionPerformed(e);
                                    }
                                 });
      
      
         btnCursor.setMaximumSize(new Dimension(30, 27));
         btnCursor.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       btnCursor_actionPerformed(e);
                                    }
                                 });
         btnCursor.setPreferredSize(new Dimension(30, 27));
         btnCursor.setToolTipText("Deselects shapes");
         btnCursor.setIcon(new ImageIcon(HomeDir+"/images/cursor.gif"));
         btnCursor.setSelected(true);
      
         btnLine.setIcon(new ImageIcon(HomeDir+"/images/lineseg.gif"));
         btnLine.setToolTipText("Line Segment");
         btnLine.setPreferredSize(new Dimension(30, 27));
         btnLine.setMaximumSize(new Dimension(30, 27));
         btnLine.setMinimumSize(new Dimension(30, 27));
         btnLine.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       ShapeButton_actionPerformed(e);
                                    }
                                 });
      
      
         btnImage.setIcon(new ImageIcon(HomeDir+"/images/blankimage.gif"));
         btnImage.setToolTipText("Image");
         btnImage.setPreferredSize(new Dimension(30, 27));
         btnImage.setMaximumSize(new Dimension(30, 27));
         btnImage.setMinimumSize(new Dimension(30, 27));
         btnImage.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       ShapeButton_actionPerformed(e);
                                    }
                                 });
         toolbarShapes.setOrientation(JToolBar.VERTICAL);
         panelLeft.setMinimumSize(new Dimension(40, 104));
         panelLeft.setPreferredSize(new Dimension(40, 136));
         btnAngleConstraint.setIcon(new ImageIcon(HomeDir+"/images/angle.gif"));
         btnAngleConstraint.setToolTipText("Angle Constraint");
         btnAngleConstraint.setPreferredSize(new Dimension(30, 27));
         btnAngleConstraint.setMaximumSize(new Dimension(30, 27));
         btnAngleConstraint.setEnabled(false);
         btnAngleConstraint.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniAngleConstr_actionPerformed(e);
                                    }
                                 });
         btnDistanceConstraint.setMinimumSize(new Dimension(30, 27));
         btnDistanceConstraint.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniDistanceConstr_actionPerformed(e);
                                    }
                                 });
         btnDistanceConstraint.setMaximumSize(new Dimension(30, 27));
         btnDistanceConstraint.setEnabled(false);
         btnDistanceConstraint.setPreferredSize(new Dimension(30, 27));
         btnDistanceConstraint.setToolTipText("Distance Constraint");
         btnDistanceConstraint.setIcon(new ImageIcon(HomeDir+"/images/distance.gif"));
         btnTangentConstraint.setMinimumSize(new Dimension(30, 27));
         btnTangentConstraint.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniTangentConstraint_actionPerformed(e);
                                    }
                                 });
         btnTangentConstraint.setMaximumSize(new Dimension(30, 27));
         btnTangentConstraint.setEnabled(false);
         btnTangentConstraint.setPreferredSize(new Dimension(30, 27));
         btnTangentConstraint.setToolTipText("Tangent Constraint");
         btnTangentConstraint.setIcon(new ImageIcon(HomeDir+"/images/tangent.gif"));
         toolbarConstraints.setOrientation(JToolBar.VERTICAL);
         if(update && (! ((mode==4) || (mode==7)) ) )
            toolbarConstraints.setEnabled(false);
         btnPerpConstraint.setIcon(new ImageIcon(HomeDir+"/images/perp.gif"));
         btnPerpConstraint.setToolTipText("Perpendicular Constraint");
         btnPerpConstraint.setPreferredSize(new Dimension(30, 27));
         btnPerpConstraint.setMaximumSize(new Dimension(30, 27));
         btnPerpConstraint.setEnabled(false);
         btnPerpConstraint.setMinimumSize(new Dimension(30, 27));
         btnPerpConstraint.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniPerpConstraint_actionPerformed(e);
                                    }
                                 });
         btnParallelConstraint.setIcon(new ImageIcon(HomeDir+"/images/parallel.gif"));
         btnParallelConstraint.setToolTipText("Parallel Constraint");
         btnParallelConstraint.setPreferredSize(new Dimension(30, 27));
         btnParallelConstraint.setMaximumSize(new Dimension(30, 27));
         btnParallelConstraint.setEnabled(false);
         btnParallelConstraint.setMinimumSize(new Dimension(30, 27));
         btnParallelConstraint.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniParallelConstraint_actionPerformed(e);
                                    }
                                 });
         btnIncidenceConstraint.setIcon(new ImageIcon(HomeDir+"/images/incident.gif"));
         btnIncidenceConstraint.setToolTipText("Incidence Constraint");
         btnIncidenceConstraint.setPreferredSize(new Dimension(30, 27));
         btnIncidenceConstraint.setMaximumSize(new Dimension(30, 27));
         btnIncidenceConstraint.setEnabled(false);
         btnIncidenceConstraint.setMinimumSize(new Dimension(30, 27));
         btnIncidenceConstraint.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniIncidenceConstr_actionPerformed(e);
                                    }
                                 });
         panelObjects.setLayout(borderLayout2);
         tabpaneEditor.setTabPlacement(JTabbedPane.BOTTOM);
         tabpaneEditor.addChangeListener(
                                 new javax.swing.event.ChangeListener()
                                 {
                                 
                                    public void stateChanged(ChangeEvent e)
                                    {
                                       tabpaneEditor_stateChanged(e);
                                    }
                                 });
         partialSketch.setLayout(borderLayout4);
         tabPartialSketch.setTabPlacement(JTabbedPane.BOTTOM);
         tabPartialSketch.addChangeListener(
                              
                                 new javax.swing.event.ChangeListener()
                                 
                                 {
                                 
                                 
                                    public void stateChanged(ChangeEvent e)
                                    
                                    {
                                       tabPartialSketch_stateChanged(e);
                                    }
                                 });								
         treeGroups.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
         treeGroups.setModel(
                               new TreeModel()
                               {
                                  public Object getChild(Object parent, int index)
                                  {
                                     return ((SKGroupTreeNode)parent).children.get(index);
                                  }
                                  public int getChildCount(Object parent)
                                  {
                                     return ((SKGroupTreeNode)parent).children.size();
                                  }
                                  public int getIndexOfChild(Object parent, Object child)
                                  {
                                     return ((SKGroupTreeNode)parent).children.indexOf(child);
                                  }
                                  public Object getRoot()
                                  {
                                     return groupTree;
                                  }
                                  public boolean isLeaf(Object node)
                                  {
                                     return (((SKGroupTreeNode)node).children.size() == 0);
                                  }
                                  public void valueForPathChanged(javax.swing.tree.TreePath path, Object newValue)
                                  {}
                                  public void addTreeModelListener(javax.swing.event.TreeModelListener l)
                                  {}
                                  public void removeTreeModelListener(javax.swing.event.TreeModelListener l)
                                  {}
                               });
         treeGroups.addMouseListener(
                                 new java.awt.event.MouseAdapter()
                                 {
                                 
                                    public void mouseClicked(MouseEvent e)
                                    {
                                       treeGroups_mouseClicked(e);
                                    }
                                 });
      
         lstGroupShapes.setModel(
                                 new ListModel()
                                 {
                                    public Object getElementAt(int index)
                                    {
                                       return selectedGroupShapes.get(index);
                                    }
                                    public int getSize()
                                    {
                                       return selectedGroupShapes.size();
                                    }
                                    public void addListDataListener(ListDataListener l)
                                    {}
                                    public void removeListDataListener(ListDataListener l)
                                    {}
                                 } );
      
         panelGroups.setLayout(borderLayout3);
         mniMakeGroup.setText("Make New Group");
         mniMakeGroup.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniMakeGroup_actionPerformed(e);
                                    }
                                 });
         mniPopMakeGroup.setText("Make New Group");
         mniPopMakeGroup.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniMakeGroup_actionPerformed(e);
                                    }
                                 });
         mniPopFixArcRadius.setText("Fix Radius of Arc");
         mniPopFixArcRadius.addActionListener(
                              
                              
                                 new java.awt.event.ActionListener()
                                 
                                 
                                 {
                                 
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    
                                    {
                                       mniFixArcRadius_actionPerformed(e);
                                    }
                                 });
         mniPopFixArcAngle.setText("Fix Angle of Arc");
         mniPopFixArcAngle.addActionListener(
                              
                              
                                 new java.awt.event.ActionListener()
                                 
                                 
                                 {
                                 
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    
                                    {
                                       mniFixArcAngle_actionPerformed(e);
                                    }
                                 });
      
         mniFixGroup.setText("Fix Group");
         mniFixGroup.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       for(int i = 0; i<selectedGroups.size(); i++)  
                                          selectedGroups.get(i).fixed = 1;
                                    }
                                 }
                              );
         mniPopFixGroup.setText("Fix Group");
         mniPopFixGroup.addActionListener(
                              
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       for(int i = 0; i<selectedGroups.size(); i++)  
                                          selectedGroups.get(i).fixed = 1;
                                    //mniMakeGroup_actionPerformed(e);
                                    }
                                 });
         mniUnFixGroup.setText("Unfix Group");
         mniUnFixGroup.addActionListener(
                              
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       for(int i = 0; i<selectedGroups.size(); i++)  
                                          selectedGroups.get(i).fixed = 0;
                                    }
                                 });
         mniPopFixGroup.setText("New Fixed Group");
         mniPopFixGroup.addActionListener(
                              
                              
                              
                                 new java.awt.event.ActionListener()
                                 
                                 
                                 {
                                 
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    
                                    {                                                                    
                                       for(int i = 0; i<selectedGroups.size(); i++) 
                                          selectedGroups.get(i).fixed = 0;
                                    }
                                 });
      
         mniMakeGrouptree.setText("Group Tree");
         mniMakeGrouptree.addActionListener(
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniMakeGrouptree_actionPerformed(e);
                                    }
                                 });
      
         mniPopMakeGrouptree.setText("Group Tree");
         mniPopMakeGrouptree.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniMakeGrouptree_actionPerformed(e);
                                    }
                                 });
      
         jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
         mniTest.setText("Get Bifurcations");
         mniTest.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       mniTest_actionPerformed(e);
                                    }
                                 });
         mniOptions.setText("Options");
         mniOptions.setEnabled(false);
         mniReStart.setText("Restart");
         mniReStart.setEnabled(false);
         mniReStart.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       PromptSaveSketch();
                                       NewData();
                                    }
                                 });
         mniAutoSolve.setText("Auto-Solve");
         mniAutoSolve.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniAutoSolve_actionPerformed(e);
                                    }
                                 });
         mniChangeCons.setText("Change constraint value");
         mniChangeCons.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniChangeCons_actionPerformed(e);
                                    }
                                 });
         mniAddCons.setText("Add Constraint");
         mniAddCons.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniAddCons_actionPerformed(e);
                                    }
                                 });
         mniRemoveCons.setText("Remove Constraint");
         mniRemoveCons.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniRemoveCons_actionPerformed(e);
                                    }
                                 });
         mniAddTree.setText("Add Tree");
         mniAddTree.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniAddTree_actionPerformed(e);
                                    }
                                 });
         mniAddShapeCon.setText("Add Shape and Constraints");
         mniAddShapeCon.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       mniAddShapeCon_actionPerformed(e);
                                    }
                                 });
         mainFrame.setLayout(borderLayout5);
         mainFrame.setBorder(BorderFactory.createLoweredBevelBorder());
         scrHoriz.setOrientation(JScrollBar.HORIZONTAL);
         scrHoriz.addAdjustmentListener(
                                 new java.awt.event.AdjustmentListener()
                                 {
                                 
                                    public void adjustmentValueChanged(AdjustmentEvent e)
                                    {
                                       scrHoriz_adjustmentValueChanged(e);
                                    }
                                 });
         scrVert.addAdjustmentListener(
                                 new java.awt.event.AdjustmentListener()
                                 {
                                 
                                    public void adjustmentValueChanged(AdjustmentEvent e)
                                    {
                                       scrVert_adjustmentValueChanged(e);
                                    }
                                 });
         scrHoriz1.setOrientation(JScrollBar.HORIZONTAL);
         scrHoriz1.addAdjustmentListener(
                              
                                 new java.awt.event.AdjustmentListener()
                                 
                                 {
                                 
                                 
                                    public void adjustmentValueChanged(AdjustmentEvent e)
                                    
                                    {
                                       scrHoriz1_adjustmentValueChanged(e);
                                    }
                                 });
         scrVert1.addAdjustmentListener(
                              
                                 new java.awt.event.AdjustmentListener()
                                 
                                 {
                                 
                                 
                                    public void adjustmentValueChanged(AdjustmentEvent e)
                                    
                                    {
                                       scrVert1_adjustmentValueChanged(e);
                                    }
                                 });
         cmbScale.setMaximumSize(new Dimension(90, 26));
         cmbScale.setEditable(true);
         cmbScale.addItemListener(
                                 new java.awt.event.ItemListener()
                                 {
                                 
                                    public void itemStateChanged(ItemEvent e)
                                    {
                                       cmbScale_itemStateChanged(e);
                                    }
                                 });
         toolbarStatus.add(sbStatus);
      
         cmbShapes.addItemListener(
                                 new java.awt.event.ItemListener()
                                 {
                                    public void itemStateChanged(ItemEvent e)
                                    {
                                       cmbShapes_itemStateChanged(e);
                                    }
                                 });
         cmbShapes.setPreferredSize(new Dimension(126, 26));
         tableObjectProp.setBackground(Color.lightGray);
         tableObjectProp.setMinimumSize(new Dimension(20, 20));
         tableObjectProp.setPreferredSize(new Dimension(170, 30));
         tableObjectProp.setModel(
                                 new AbstractTableModel()
                                 {
                                    public int getColumnCount() { 
                                       return 2; }
                                    public int getRowCount() { 
                                       return vCurrentProps.size(); }
                                    public Object getValueAt(int row, int col)
                                    {
                                       if (col==0)
                                       {
                                          return vCurrentProps.getShapePropName(row);
                                       }
                                       else
                                       {
                                          return vCurrentProps.get(row);
                                       }
                                    }
                                    public boolean isCellEditable(int rowIndex, int columnIndex)
                                    {
                                       if(update && (!(mode==3)))
                                          return false;
                                       else
                                          return (columnIndex==1 && vCurrentProps.get(rowIndex).isEditable);
                                    }
                                    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
                                    {
                                       //Only update if new value and old value differ
                                       if (!vCurrentProps.getShapePropData(rowIndex).toString().equals(aValue.toString()))
                                       {
                                          vCurrentProps.setShapePropData(rowIndex,aValue.toString());
                                       
                                          String PropName = vCurrentProps.getShapePropName(rowIndex);
                                          Object PropData = vCurrentProps.getShapePropData(rowIndex);
                                          if (editingShape)
                                          {
                                           //Have selected shapes update themselves
                                             for (int i=0; i<SelectedShapes.size(); i++)
                                             {
                                                SelectedShapes.get(i).setShapeData(PropName,PropData);
                                             }
                                          
                                           //Update name in cmbShapes?
                                             if (vCurrentProps.getShapePropName(rowIndex)=="Name")  cmbShapes.repaint();
                                          }
                                          else
                                          { //editing a constraint
                                             SelectedConstraint.setMainData(PropName,PropData);
                                          }
                                       }
                                    }
                                 });
         tableObjectProp.getColumnModel().getColumn(1).setCellRenderer(new SKTableCellRenderer());
         tableObjectProp.getColumnModel().getColumn(1).setCellEditor(new MultiCellEditor());
      
         treeConstraints.setModel(
                                 new TreeModel()
                                 {
                                    public Object getChild(Object parent, int index)
                                    {
                                       if (parent.toString()=="Constraints")
                                       { //Constraint Names
                                          switch (index)
                                          {
                                             case 0  : 
                                                return "Distance";
                                             case 1  : 
                                                return "Incidence";
                                             case 2  : 
                                                return "Angle";
                                             case 3  : 
                                                return "Parallel";
                                             case 4  : 
                                                return "Perpendicular";
                                             case 5  :
                                                return "Tangent";
                                             default : 
                                                return "";
                                          }
                                       }
                                       else if (parent.toString()=="Shapes")
                                       {
                                          return SelectedConstraint.ShapeList.get(index);
                                       }
                                       else
                                       { //Constraints
                                          int i, cnt=0;
                                          for (i=0; i<vCurrentConstraints.size() && cnt!=index+1; i++)
                                          {
                                             if ( parent.toString().equals(vCurrentConstraints.get(i).cType) )
                                                cnt++;
                                          }
                                       
                                          return vCurrentConstraints.get(i-1);
                                       }
                                    }
                                    public int getChildCount(Object parent)
                                    {
                                       if (parent.toString()=="Constraints")
                                       {
                                          return 6;
                                       }
                                       else if (parent.toString()=="Shapes")
                                       {
                                          if (SelectedConstraint==null)
                                             return 0;
                                          else
                                             return SelectedConstraint.ShapeList.size();
                                       }
                                       else
                                       {
                                          int cnt=0;
                                          for (int i=0; i<vCurrentConstraints.size(); i++)
                                          {
                                             if ( parent.toString().equals(vCurrentConstraints.get(i).cType) )
                                                cnt++;
                                          }
                                       
                                          return cnt;
                                       }
                                    }
                                    public int getIndexOfChild(Object parent, Object child)
                                    {
                                       if (parent.toString()=="Constraints")
                                       { //Don't care about Constraint Names
                                          return 0;
                                       }
                                       else
                                       {
                                          return vCurrentConstraints.indexOf(child);
                                       }
                                    }
                                    public Object getRoot()
                                    {
                                       if (editingShape)
                                          return "Constraints";
                                       else
                                          return "Shapes";
                                    }
                                    public boolean isLeaf(Object node)
                                    {
                                       if (node instanceof String) 
                                          return false;
                                    
                                       return true;
                                    }
                                    public void valueForPathChanged(javax.swing.tree.TreePath path, Object newValue)
                                    {
                                    
                                    }
                                    public void addTreeModelListener(javax.swing.event.TreeModelListener l)
                                    {
                                    
                                    }
                                    public void removeTreeModelListener(javax.swing.event.TreeModelListener l)
                                    {
                                    
                                    }
                                 });
         tabpaneObjectProp.setMinimumSize(new Dimension(45, 49));
         panelEditor.setLayout(borderLayout1);
         panelShapeArea.setLayout(null);
         panelPartial.setLayout(null);
         mniCut.setText("Cut");
         mniCut.setIcon(new ImageIcon(HomeDir+"/images/cut.gif"));
         mniCopy.setText("Copy");
         mniCopy.setIcon(new ImageIcon(HomeDir+"/images/copy.gif"));
         mniPaste.setText("Paste");
         mniPaste.setIcon(new ImageIcon(HomeDir+"/images/paste.gif"));
         mniEditDelete.setText("Delete");
         mniEditDelete.setIcon(new ImageIcon(HomeDir+"/images/delete.gif"));
         mniEditDelete.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {mniDelete_actionPerformed(e);
                                    }
                                 });
         lblScale.setText("Scale ");
         jMenuBar1.add(mniFile);
         jMenuBar1.add(mniEdit);
         jMenuBar1.add(mniDesign);
         jMenuBar1.add(mniSolveSketch);
         jMenuBar1.add(mniOptions);
         jMenuBar1.add(mniRepository);
         jMenuBar1.add(mniView);
         jMenuBar1.add(mniHelp);
         mniFile.add(mniNew);
         mniFile.add(mniOpen);
         mniFile.add(mniReopen);
         mniFile.add(mniSave);
         mniFile.add(mniSaveAs);
         mniFile.addSeparator();
         mniFile.add(mniPref);
         mniFile.addSeparator();
         mniFile.add(mniExit);
         mniOptions.add(mniChangeCons);
         mniOptions.add(mniAddCons);
         mniOptions.add(mniRemoveCons);
         mniOptions.add(mniAddTree);
         mniOptions.add(mniAddShapeCon);
         mniSolveSketch.add(mniSolve);
         mniSolveSketch.add(mniSolveOptions);
         mniSolveOptions.add(mniTest);
         mniSolveOptions.add(mniAutoSolve);
         mniSolveSketch.add(mniReStart);
         //mniSolveSketch.add(mniAutoSolve);
         //mniSolveSketch.add(mniTest);
      
         this.getContentPane().add(toolbarMain, BorderLayout.NORTH);
         toolbarMain.add(btnNew, null);
         toolbarMain.add(btnOpen, null);
         toolbarMain.add(btnSave, null);
         toolbarMain.add(btnSaveAs, null);
         toolbarMain.addSeparator();
         toolbarMain.add(btnExit, null);
         toolbarMain.addSeparator();
         toolbarMain.addSeparator();
         toolbarMain.addSeparator();
         toolbarMain.add(lblScale, null);
         toolbarMain.add(cmbScale, null);
         this.getContentPane().add(tabPartialSketch, BorderLayout.CENTER);
         mainFrame.add(panelShapeArea, BorderLayout.CENTER);
         partialSketch.add(panelPartial, BorderLayout.CENTER);
         tabPartialSketch.addTab("Original Sketch",mainFrame);
         tabPartialSketch.addTab("Partially solved sketch",partialSketch);
         this.getContentPane().add(toolbarEditor, BorderLayout.EAST);
         toolbarEditor.add(panelEditor, null);
         panelEditor.add(tabpaneEditor, BorderLayout.CENTER);
         tabpaneEditor.add(panelObjects, "Objects");
         panelObjects.add(cmbShapes, BorderLayout.NORTH);
         panelObjects.add(tabpaneObjectProp, BorderLayout.CENTER);
         tabpaneObjectProp.add(tableObjectProp, "Properties");
         tabpaneObjectProp.add(treeConstraints, "Constraints");
         tabpaneEditor.add(panelGroups, "Groups");
         panelGroups.add(jSplitPane1, BorderLayout.CENTER);
         jSplitPane1.add(treeGroups, JSplitPane.TOP);
         jSplitPane1.add(lstGroupShapes, JSplitPane.BOTTOM);
         this.getContentPane().add(toolbarStatus, BorderLayout.SOUTH);
         this.getContentPane().add(panelLeft, BorderLayout.WEST);
         panelLeft.add(toolbarShapes, null);
         toolbarShapes.add(btnCursor, null);
         toolbarShapes.add(btnPoint, null);
         toolbarShapes.add(btnLine, null);
         toolbarShapes.add(btnCircle, null);
         toolbarShapes.add(btnArc, null);
         toolbarShapes.add(btnImage,null);
         panelLeft.add(toolbarConstraints, null);
         toolbarConstraints.add(btnAngleConstraint, null);
         toolbarConstraints.add(btnDistanceConstraint, null);
         toolbarConstraints.add(btnIncidenceConstraint, null);
         toolbarConstraints.add(btnParallelConstraint, null);
         toolbarConstraints.add(btnPerpConstraint, null);
         toolbarConstraints.add(btnTangentConstraint, null);
         mniHelp.add(mniAbout);
      
      //Group Shape buttons together
         bgShapes.add(btnCursor);
         bgShapes.add(btnPoint);
         bgShapes.add(btnLine);
         bgShapes.add(btnCircle);
         bgShapes.add(btnArc);
         bgShapes.add(btnAngleConstraint);
         bgShapes.add(btnDistanceConstraint);
         bgShapes.add(btnIncidenceConstraint);
         bgShapes.add(btnParallelConstraint);
         bgShapes.add(btnPerpConstraint);
         bgShapes.add(btnTangentConstraint);
         bgShapes.add(btnImage);
      
         popupShape.add(mniPopCut);
         popupShape.add(mniPopCopy);
         popupShape.add(mniPopPaste);
         popupShape.add(mniPopSelectAll);
         popupShape.addSeparator();
         popupShape.add(mniPopFixArcRadius);
         popupShape.add(mniPopFixArcAngle);
         popupShape.addSeparator();
         popupShape.add(mniDelete);
         popupShape.addSeparator();
         popupShape.add(mniNewConstraint);
         popupShape.addSeparator();
         popupShape.add(mniPopMakeGroup);        
         popupShape.add(mniPopMakeGrouptree);
      
         popupGroup.add(mniPopFixGroup);
         popupGroup.add(mniPopUnFixGroup);
      
         mniEdit.add(mniCut);
         mniEdit.add(mniCopy);
         mniEdit.add(mniPaste);
         mniEdit.add(mniSelectAll);
         mniEdit.addSeparator();
         mniEdit.add(mniEditDelete);
         mniEdit.addSeparator();
         mniNewConstraint.add(mniAngleConstr);
         mniNewConstraint.add(mniDistanceConstr);
         mniNewConstraint.add(mniIncidenceConstr);
         mniNewConstraint.add(mniPerpConstraint);
         mniNewConstraint.add(mniParallelConstraint);
         mniNewConstraint.add(mniTangentConstraint);
         mniView.add(mniDrawConstraints);
         mniRepository.add(mniRepositoryViewer);
         mniRepository.addSeparator();
         mniRepository.add(mniNewLibrary);
         mniDesign.add(mniNewTree);
         mniDesign.add(mniMakeGroup);
         mniDesign.add(mniMakeGrouptree);
         mniDesign.add(mniFixGroup);
         mniDesign.add(mniUnFixGroup);
         popTree.add(mniTreeDelete);
         mainFrame.add(scrHoriz, BorderLayout.SOUTH);
         mainFrame.add(scrVert, BorderLayout.EAST);
         partialSketch.add(scrHoriz1, BorderLayout.SOUTH);
         partialSketch.add(scrVert1, BorderLayout.EAST);
      
      //Setup statusbar
         sbStatus.addPanel("Ready",55,sbStatus.ALIGN_CENTER); //Status
         sbStatus.addPanel("Modified",50,sbStatus.ALIGN_CENTER); //Modified
         sbStatus.addPanel("",70,sbStatus.ALIGN_CENTER); //Distance
         sbStatus.addPanel("",80,sbStatus.ALIGN_CENTER); //Shape name that mouse it over
         sbStatus.addPanel("Untitled",50,sbStatus.ALIGN_LEFT); //Current filename
      
         tabpaneObjectProp.setEnabled(false);
      
         cmbShapes.setRenderer( new ImageComboBoxRenderer() );
      
         scrHoriz.setValues(0,panelShapeArea.getWidth(),-1000,1000);
         scrHoriz.setUnitIncrement(10);
         scrVert.setValues(0,panelShapeArea.getHeight(),-1000,1000);
         scrVert.setUnitIncrement(10);
      
         scrHoriz1.setValues(0,panelPartial.getWidth(),-1000,1000);
         scrHoriz1.setUnitIncrement(10);
         scrVert1.setValues(0,panelPartial.getHeight(),-1000,1000);
         scrVert1.setUnitIncrement(10);
      
         for(int i=0; i<scaleValues.length; i++)
            cmbScale.addItem(scaleValues[i]);
         cmbScale.setSelectedIndex(1);
      
         repository = new SKRepository(this);
         repository.setVisible(false);
      
         mruManager = new MRUManager(8,this);
         mruManager.readMRUfromFile(HomeDir+"/mruData.dat");
         mruManager.syncMenu(mniReopen);
         jSplitPane1.setDividerLocation(260);
      }
   
      public void MRUclicked(MRUItem item)
      {
         LoadFile( item.data );
      }
   
      void mniExit_actionPerformed(ActionEvent e)
      {
         if(dataExists)
         {
            if (PromptSaveSketch())
            {
               System.exit(0);
            }
         }
         else if (PromptSaveChanges())
         {
            System.exit(0);
         }
      }
   
      void mniNew_actionPerformed(ActionEvent e)
      {
         if(dataExists)
         { 
            if (!PromptSaveSketch()) 
               return;}
         else if(bDataModified)
         { 
            if (!PromptSaveChanges()) 
               return;}
      
         NewData();
      }
   
      void mniOpen_actionPerformed(ActionEvent e)
      {
         OpenFile();
      }
   
      void this_windowClosing(WindowEvent e)
      {
         if(dataExists)
         {
            if (PromptSaveSketch())
            {
               System.exit(0);
            }
         }
         else if (PromptSaveChanges())
         {
            System.exit(0);
         }
      }
   
      void mniDelete_actionPerformed(ActionEvent e)
      {
         cmbShapes.setSelectedIndex(-1);
         for (int i=0; i<SelectedShapes.size(); i++)
         {
            deleteItem(SelectedShapes.get(i));}
      
         clearSelectedShapes(true,false);
         if(deleteConstraint!=null)
            deleteItem(deleteConstraint);
      
         panelShapeArea.repaint();
      }
   
      void cmbShapes_itemStateChanged(ItemEvent e)
      { //Make this is selected item
         if (e.getStateChange()==e.SELECTED && Updating==0 && cmbShapes.getSelectedIndex() != -1)
         {
            Updating++;
            addOnlySelectedShape( (SKBaseShape)cmbShapes.getSelectedItem() );
            Updating--;
         }
      }
      void mniSaveAs_actionPerformed(ActionEvent e)
      
      {
         if(dataExists)
            PromptSaveAsSketch();
         else InternalSaveAs();
      }
      void mniSave_actionPerformed(ActionEvent e)
      {
         if(dataExists)
            PromptSaveSketch();
         else InternalSaveChanges();
      }
   
      void mniEdit_menuSelected(MenuEvent e)
      {
        // for(int i=0; i<allshapes.size(); i++)
        //    System.out.println(allshapes.get(i)+" "+allshapes.get(i).ShapeTypeID);
      
         boolean b = (SelectedShapes.size() != 0);
         boolean c = (SelectedConstraint != null);
         mniCut.setEnabled(b);
         mniCopy.setEnabled(b);
         if(update && (!(mode==5)))
            mniEditDelete.setEnabled(false);
         else
            mniEditDelete.setEnabled(b || c);
      
         mniPaste.setEnabled(ClipShapes.size()>0);
      }
   
      void mniDesign_menuSelected(MenuEvent e)
      
      {
         if(update &&(!(mode==6)))
            mniMakeGrouptree.setEnabled(false);
         else
            mniMakeGrouptree.setEnabled(b1);
         if(update &&(!(mode==6)))
            mniMakeGroup.setEnabled(false);
         else
            mniMakeGroup.setEnabled(b1);
         mniFixGroup.setEnabled(selectedGroups.size()>0); 
         mniUnFixGroup.setEnabled(selectedGroups.size()>0);        
      }
   
   
      void panelShapeArea_mouseDragged(MouseEvent e)
      {
      
         if (!btnCursor.isSelected())
         {
            panelShapeArea_mouseClicked(e);
            return;
         }
         dragged = true;
         /*if (mouseOverItem != null && mouseOverItem instanceof SKBaseShape)
         {SKBaseShape sh = (SKBaseShape)mouseOverItem;
            if (sh.isSelected())
               for(int i=0; i<sh.ConstraintList.size(); i++)
                  sh.ConstraintList.get(i).solved =false;
         }
       For dragging DrawnItems (dragging lineShape by line)
      if (mouseOverItem != null && mouseOverItem instanceof SKBaseShape)
      { //Drag shape
      SKBaseShape sh = (SKBaseShape)mouseOverItem;
      if (sh.isSelected())
      {
        if (sh.DragState==-1)
        {
          sh.DragState = 0;
          sh.DragX = e.getX();
          sh.DragY = e.getY();
          sh.setCursor( Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR) );
        }
        else
        { //Draw as user is dragging
          sh.doMove(e.getX()-sh.DragX,e.getY()-sh.DragY,(SKOptions.byteOptions[ SKOptions.simpleSolverMode ]==2));
        }
      
        RefreshShapeArea();
      }
      }
      else
      { //Do selection rect
      */
         if (!panelShapeArea.DrawSelRect)
         { panelShapeArea.DrawSelRect = true;
         
            panelShapeArea.StartX = e.getX();
            panelShapeArea.StartY = e.getY();
         }
         else
         { //Draw selection rect
            panelShapeArea.EndX = e.getX();
            panelShapeArea.EndY = e.getY();
            panelShapeArea.repaint();
         }
      //    }
      }
   
      public float distance(int x1, int y1, int x2, int y2)
      {
         return (float)java.lang.Math.sqrt(java.lang.Math.pow((double)(x1-x2),2)+java.lang.Math.pow((double)(y1-y2),2));
      }
   
      void panelShapeArea_mouseReleased(MouseEvent e)
      {
         if (mouseOverItem != null && mouseOverItem instanceof SKBaseShape)
            ((SKBaseShape)mouseOverItem).DragState = -1;
      
         if(SelectedShapes.size()==1)
            if((SelectedShapes.get(0) instanceof SKCircleShape) )
            {
            
               SKCircleShape circle = (SKCircleShape) SelectedShapes.get(0);
               int distance = (int)distance(panelShapeArea.StartX,panelShapeArea.StartY,circle.center.getShapeX(),circle.center.getShapeY());
               int rad=20;
               if(circle.radius!=-1) rad=(int)circle.radius;
               //System.out.println("reached here 1"+distance+" "+rad+" "+e.getX()+" "+circle.center.getX());
            
               if(dragged )
               
               {
                  if((distance<(rad+5)) && (distance>(rad-5)))
                     circle.increaseRadius(e.getX(),e.getY());
               }
                                        //((SKCircleShape)sh).radius=java.lang.Math.sqrt(java.lang.Math.pow((double)(((SKCircleShape)sh).center.getX()-e.getX()),2)+java.lang.Math.pow((double)(((SKCircleShape)sh).center.getY()-e.getY()),2));
            }
         dragged = false;
         if (e.isPopupTrigger())
         {
            popupShape.show(panelShapeArea,e.getX(),e.getY());
            return;
         }
         if (panelShapeArea.DrawSelRect)
         { //Selected shapes in rect and repaint
            if (!e.isControlDown()) clearSelectedShapes(true,true);
         
            int sx,sy,ex,ey;
            if (panelShapeArea.StartX<panelShapeArea.EndX)
            {
               sx = panelShapeArea.StartX;
               ex = panelShapeArea.EndX;
            }
            else
            {
               sx = panelShapeArea.EndX;
               ex = panelShapeArea.StartX;
            }
            if (panelShapeArea.StartY<panelShapeArea.EndY)
            {
               sy = panelShapeArea.StartY;
               ey = panelShapeArea.EndY;
            }
            else
            {
               sy = panelShapeArea.EndY;
               ey = panelShapeArea.StartY;
            }
         
            SKBaseShape sh;
            for (int i=0; i<panelShapeArea.getComponentCount(); i++)
            {
               sh = (SKBaseShape)panelShapeArea.getComponent(i);
               if (sh.getX()>sx && (sh.getX()+sh.getWidth())<ex &&
                  sh.getY()>sy && (sh.getY()+sh.getHeight()<ey))
               {
                  addSelectedShape(sh);
               }
            }
         
            panelShapeArea.DrawSelRect=false;
            panelShapeArea.repaint();
         }
      }
   
      void panelGroups_mouseReleased(MouseEvent e)
      
      {
         if (e.isPopupTrigger())
         {
            popupGroup.show(panelGroups,e.getX(),e.getY());
            return;
         }
         panelGroups.repaint();
      }
      void mniSelectAll_actionPerformed(ActionEvent e)
      {
         selectAllShapes();
      }
   
      void mniCut_actionPerformed(ActionEvent e)
      {
         cutSelectedShapes();
      }
   
      void mniCopy_actionPerformed(ActionEvent e)
      {
         copySelectedShapes();
      }
   
      void mniPaste_actionPerformed(ActionEvent e)
      {
         pasteClipShapes();
      }
   
      void popupShape_popupMenuWillBecomeVisible(PopupMenuEvent e)
      {
         boolean b = (SelectedShapes.size() > 0);
         boolean c = (SelectedConstraint!=null) ;
         mniPopCut.setEnabled(b);
         mniPopCopy.setEnabled(b);
         mniPopFixArcRadius.setEnabled(SelectedShapes.size()==1 && SelectedShapes.get(0) instanceof SKArcShape);
         mniPopFixArcAngle.setEnabled(SelectedShapes.size()==1 && SelectedShapes.get(0) instanceof SKArcShape);
         mniDelete.setEnabled(b || c);
         mniPopMakeGroup.setEnabled(b1);
         mniPopMakeGrouptree.setEnabled(b1); 
         mniNewConstraint.setEnabled(SelectedShapes.size()>1);
         mniPopSelectAll.setEnabled(panelShapeArea.getComponentCount()>0);
         mniPopPaste.setEnabled(ClipShapes.size()>0);
      }
   
      void popupGroup_popupMenuWillBecomeVisible(PopupMenuEvent e)
      
      {
         mniPopFixGroup.setEnabled(selectedGroups.size()>0);
         mniPopUnFixGroup.setEnabled(selectedGroups.size()>0);
      
      }
   
   
      void mniPref_actionPerformed(ActionEvent e)
      { //Create and show SKOptionFrame
         SKOptionDialog fr = new SKOptionDialog(this,"Preferences",true,HomeDir);
         fr.show();
      
      //Used when enabling SimpleSolving
         if (fr.needRedoAllSolving)
         {
            SKConstraintArray cons = new SKConstraintArray(5);
            SKBaseShape sh;
            for (int i=0; i<panelShapeArea.getComponentCount(); i++)
            {
               sh = (SKBaseShape)panelShapeArea.getComponent(i);
               if (sh.getSelectable().isPrimaryShape(sh))
                  SKSimpleSolver.ResolveSimpleConstraints(sh.getSelectable(),cons);
            }
         }
      
         fr.dispose();
      
         RefreshShapeArea();
      }
   
      void mniDistanceConstr_actionPerformed(ActionEvent e)
      {
         InitConstraint( createConstraint(0,++ConstrIDCnt), 2, 2);
      }
   
      void mniIncidenceConstr_actionPerformed(ActionEvent e)
      {
         InitConstraint( createConstraint(1,++ConstrIDCnt), 2, 2);
      }
   
      void mniPerpConstraint_actionPerformed(ActionEvent e)
      {
         InitConstraint( createConstraint(2,++ConstrIDCnt), 2, 2);
      }
   
      void mniParallelConstraint_actionPerformed(ActionEvent e)
      {
         InitConstraint( createConstraint(3,++ConstrIDCnt), 2, 2);
      }
   
      void mniAngleConstr_actionPerformed(ActionEvent e)
      {
         InitConstraint( createConstraint(4,++ConstrIDCnt), 2, 2);
      }
   
      void mniTangentConstraint_actionPerformed(ActionEvent e)
      {
         InitConstraint( createConstraint(5,++ConstrIDCnt), 2, 2);
      }
   
      void ShapeButton_actionPerformed(ActionEvent e)
      {
         panelShapeArea.setCursor( Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR) );
      }
   
      void btnCursor_actionPerformed(ActionEvent e)
      {
         panelShapeArea.setCursor( Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
      }
   
      void chkDrawConstraints_actionPerformed(ActionEvent e)
      {
         panelShapeArea.DrawConstraints=!panelShapeArea.DrawConstraints;
      
         panelShapeArea.repaint();
      }
   
      void mniRepositoryViewer_actionPerformed(ActionEvent e)
      {
         repository.setVisible( !repository.isVisible() );}
   
      void mniView_menuSelected(MenuEvent e)
      {
         mniRepositoryViewer.setState( repository.isVisible() );
      }
   
      void panelShapeArea_mouseMoved(MouseEvent e)
      {
      //Update mouse coordinates on statusbar
      //sbStatus.updatePanelText(e.getX()+" , "+e.getY(),2);
      
         boolean wasNull = (mouseOverItem==null);
         if (mouseOverItem==null || !mouseOverItem.doHitTest(e.getX(),e.getY()) )
         {
            for (int i=0; i<DrawnItems.size(); i++)
            {
               mouseOverItem = DrawnItems.get(i);
               if ( mouseOverItem.doHitTest(e.getX(),e.getY()) )
               {
                  sbStatus.updatePanelText( mouseOverItem.toString(),3);
                  panelShapeArea.repaint();
                  return;
               }
            }
         
            mouseOverItem=null;
            sbStatus.updatePanelText("",3);
            if (!wasNull)  panelShapeArea.repaint();
         }
      }
   
      void treeConstraints_mouseClicked(MouseEvent e)
      {
         if (e.getClickCount()==2)
         { //Start editing this constraint, if one is selected
            TreePath Path = treeConstraints.getPathForLocation(e.getX(),e.getY());
            if (Path==null)  
               return;
            Object ob = Path.getLastPathComponent();
            if (ob instanceof SKBaseConstraint)
            {
               EditConstraint( (SKBaseConstraint)ob );
            }
            else if (ob instanceof SKBaseShape)
            {
               addSelectedShape( (SKBaseShape)ob );
            }
         }
      }
   
      void mniRepository_menuSelected(MenuEvent e)
      {
         mniRepositoryViewer.setState( repository.isVisible() );
      }
   
      void treeConstraints_keyTyped(KeyEvent e)
      {
         if (e.getKeyChar()=='\b' || e.getKeyCode()==e.VK_DELETE)
            deleteItem(treeConstraints.getSelectionPath().getLastPathComponent());
      }
   
      void mniTreeDelete_actionPerformed(ActionEvent e)
      {
         deleteItem(treeConstraints.getSelectionPath().getLastPathComponent());
      }
   
      void mniMakeGroup_actionPerformed(ActionEvent e)
      {
         b2=true;
         ++GroupIDCnt;
         SKGroupTreeNode newNode1 = (SKGroupTreeNode)SKGroups.makeGrouptree(groupTree,SelectedShapes,GroupIDCnt,groupArray,treeID-2,fixed);
         groupArray.add(newNode1);
      
         for(int a=0; a<groupArray.size(); a++)
         {
            for(int b=0; b<newNode1.parents.size(); b++)
            {
               if(groupArray.get(a)==newNode1.parents.get(b))
               {SKGTNArray temp=new SKGTNArray(100);
                  groupArray.get(a).children.copyArrayTo(temp);
                  groupArray.copyArrayTo(groupArray.get(a).children);
                  int d=0;
                  while (d<groupArray.get(a).children.size())
                  {
                     if(temp.size()==0)
                     { 
                        if(groupArray.get(a).children.get(d)!=newNode1)
                        {groupArray.get(a).children.removeNode(groupArray.get(a).children.get(d));
                           d--;}}
                     else
                     {boolean belongs=false;
                        for(int c=0; c<temp.size(); c++)
                           belongs=belongs||(groupArray.get(a).children.get(d)==temp.get(c));
                        if(!belongs&&groupArray.get(a).children.get(d)!=newNode1 )
                        { groupArray.get(a).children.removeNode(groupArray.get(a).children.get(d));
                           d--;}
                     }
                     d++;
                  }
               }  
            }
            for(int b=0; b<newNode1.children.size(); b++)
            {
               if(groupArray.get(a)==newNode1.children.get(b))
               {
                  SKGTNArray temp=new SKGTNArray(100);
                  groupArray.get(a).parents.copyArrayTo(temp);
                  groupArray.copyArrayTo(groupArray.get(a).parents);
                  int d=0;
                  while (d<groupArray.get(a).parents.size())
                  {
                     if(temp.size()==0)
                     { 
                        if( groupArray.get(a).parents.get(d)!=newNode1)
                        {groupArray.get(a).parents.removeNode(groupArray.get(a).parents.get(d));
                           d--;}}
                     else
                     {boolean belongs=false;
                        for(int c=0; c<temp.size(); c++)
                           belongs=belongs||(groupArray.get(a).parents.get(d)==temp.get(c));
                        if(!belongs && groupArray.get(a).parents.get(d)!=newNode1)
                        {groupArray.get(a).parents.removeNode(groupArray.get(a).parents.get(d));
                           d--;}
                     }
                     d++;
                  }
               
               }
            }
         }
      
      
         treeGroups.updateUI();
      }
   
      void mniFixArcRadius_actionPerformed(ActionEvent e)
      {
         if(((SKArcShape)SelectedShapes.get(0)).fixradius ==false)((SKArcShape)SelectedShapes.get(0)).fixradius = true;
         else ((SKArcShape)SelectedShapes.get(0)).fixradius = false;
      }
      void mniFixArcAngle_actionPerformed(ActionEvent e)
      
      {
         if(((SKArcShape)SelectedShapes.get(0)).fixangle == false)((SKArcShape)SelectedShapes.get(0)).fixangle =true;
         else ((SKArcShape)SelectedShapes.get(0)).fixangle =false;
      }
   
      void mniMakeGrouptree_actionPerformed(ActionEvent e)
      
      {
      
         File f = new File("");
         String st = f.getAbsolutePath();
         for(int i=0; i<groupArray.size(); i++)
         {
            boolean present=false;
            for(int a=0; a<allGroups.size(); a++)
               present = present || (allGroups.get(a).name.equals(groupArray.get(i).name));
            if (!present)allGroups.add(groupArray.get(i));   
         }
         int[] fin = new int[allGroups.size()];
         TreeFrame frame = new TreeFrame(this,allGroups,0,fin);
      
         frame.show();
      
      }
   
   
   
      void mniSolve_actionPerformed(ActionEvent e)
      {
         SKUTU solver = new SKMapleUTU();
      
      //Put all shapes into a SKShapeArray
         selectAllShapes();
      
      //Put all constraints into a SKConstraintArray
         SKConstraintArray cons = new SKConstraintArray( DrawnItems.size() );
         for (int i=0; i<DrawnItems.size(); i++)
            if (DrawnItems.get(i) instanceof SKBaseConstraint)
               cons.add( (SKBaseConstraint)DrawnItems.get(i) );
      
         solver.solveSystem(this,SelectedShapes,cons);
      }
   
   
      void mniNewTree_actionPerformed(ActionEvent e)
      
      {         
         for(int i=0; i<groupArray.size(); i++)
            allGroups.add(groupArray.get(i));  
         groupArray.clear();
         SKGroupTreeNode   newGroupTree = new SKGroupTreeNode(groupTree,treeID,"User"+treeID);
         //SKGTNArray groupArray = new SKGTNArray(100);
         groupTree.children.add(newGroupTree);
         groupArray.add(newGroupTree);
         treeID++;
         b1=true;
         GroupIDCnt=0;
      }
   
   
      void treeGroups_mouseClicked(MouseEvent e)
      {
      //Update group selection
         int i;
         for (i=0; i<selectedGroupShapes.size(); i++)
            selectedGroupShapes.get(i).highestGroup = null;
      
         for (i=0; i<selectedGroups.size(); i++)
            selectedGroups.get(i).groupRect.w = 0;
      
         selectedGroupShapes.clear();
         selectedGroups.clear();
      
         TreePath path;
         for (i=0; i<treeGroups.getSelectionCount(); i++)
         {
            path = treeGroups.getSelectionPaths()[i];
            if (path.getPathCount()>1)
            {
               selectedGroups.add( (SKGroupTreeNode)path.getLastPathComponent() );
               SKGroups.getAllGroupShapes( (SKGroupTreeNode)path.getLastPathComponent(), selectedGroupShapes, true );
            
            }
         }
      
         for (i=0; i<selectedGroupShapes.size(); i++)
            selectedGroupShapes.get(i).highestGroup = SKGroups.getHighestGroupWithShape(selectedGroupShapes.get(i),selectedGroups);
      
         drawGroupRects = (SKOptions.byteOptions[ SKOptions.groupDisplayMode ] == 1 && selectedGroups.size() > 0);
      
         lstGroupShapes.updateUI();
         RefreshShapeArea();
      }
   
      void mniAutoSolve_actionPerformed(ActionEvent e)
      
      {
         if(!update)
         {
            mode = 1;
            IntArray = new int[3000];
            DblArray = new double[1000];
            SKUTUFile.writeUTUFile(IntArray,DblArray,allshapes,groupTree);
            update = true;
            mniReStart.setEnabled(true);
            mniOptions.setEnabled(true);
            dataExists=true;
         }
         else
         {
            switch(mode)
            {
               case 3:
                  IntArray[0]=13;
                  mode = 13;
                  break;
               case 4:
                  IntArray[0]=14;
                  mode = 14;
                  break;
               case 5:
                  IntArray[0]=15;
                  mode = 15;
                  break;
               case 6:
                  IntArray[0]=16;
                  mode = 16;
                  break;
               case 7:
                  IntArray[0]=17;
                  mode = 17;
                  break;
               default:
                  IntArray[0]=1;
                  mode=1;
                  break;
            }
            System.out.println("main frame1"+ IntArray[0]);
            utuJava utuDriver = new utuJava();
            utuDriver.utuC(IntArray, DblArray);
         
         }
         solve();
      }
      void mniTest_actionPerformed(ActionEvent e)
      {
         if(!update)
         {
            mode = 2;
            IntArray = new int[3000];
            DblArray = new double[1000];
            SKUTUFile.writeUTUFile(IntArray,DblArray,allshapes,groupTree);
            update = true;
            mniReStart.setEnabled(true);
            mniOptions.setEnabled(true);
            dataExists=true;
         }
         else
         {
            switch(mode)
            {
               case 3: 
                  break;
               case 4:
                  break;
               case 5:
                  break;
               case 6:
                  break;
               case 7:
                  break;
               default :
                  IntArray[0]=2;
                  utuJava utuDriver = new utuJava();
                  utuDriver.utuC(IntArray, DblArray);
                  break;
            }
         }
         solve();
      }
      void mniChangeCons_actionPerformed(ActionEvent e)
      
      {
         mode = 3;
         update = true;
      }
   
   
      void mniAddCons_actionPerformed(ActionEvent e)
      
      {
         mode = 4;
         update = true;
      
      }
   
      void mniRemoveCons_actionPerformed(ActionEvent e)
      
      {
         mode = 5;
         update = true;
      }
   
      void mniAddTree_actionPerformed(ActionEvent e)
      
      {
         mode = 6;
         update = true;
      
      }
   
      void mniAddShapeCon_actionPerformed(ActionEvent e)
      
      {
         mode = 7;
         update = true;
      }
   
      void solve()
      {
            //SKUTUFile.writeUTUFile(HomeDir+"/utu.dat",panelShapeArea,groupTree);
         boolean solve = true;
         System.out.println("main frame2"+ mode);
         utuJava utuDriver = new utuJava();
         int lengthi, lengthd;
      
         sbStatus.updatePanelText("Solving...",0);
         selectAllShapes();
         switch(mode)
         {
            case 0:
               break;
            case 1:
               break;
            case 2:
               break;
            case 3:
               lengthi = IntArray[0];
               lengthd = (int)DblArray[0];
               IntArray[0]=mode;
               boolean changed = false, flag = false;
               for(int i = 0; i<allConstraints.size(); i++)
               {
                  if(allConstraints.get(i) instanceof SKDistanceConstraint)
                  {
                     SKDistanceConstraint con =(SKDistanceConstraint)allConstraints.get(i);
                  //SKDistanceConstraint con = (SKDistanceConstraint)oldConstraints.findByID(allConstraints.get(i).ID);
                     if(con.distance != consArray[con.ID]) 
                     {
                        IntArray[lengthi++] = con.ID;
                        DblArray[lengthd++] = con.distance;
                        changed = false;
                        flag = true;
                     }
                  }
                  if(oldConstraints.get(i) instanceof SKAngleConstraint)
                  {
                     SKAngleConstraint con = (SKAngleConstraint)allConstraints.findByID(oldConstraints.get(i).ID);
                     changed = changed || ((int)con.angle != (int)((SKAngleConstraint)oldConstraints.get(i)).angle);
                     if(changed) 
                     {
                        IntArray[lengthi++] = con.ID;
                        DblArray[lengthd++] = con.angle;
                        changed = false;
                        flag = true;
                     }
                  }
               }
               if(flag)
               {
                  for (int x= IntArray[0]; x<IntArray[0]+30 ; x++)
                     System.out.println(IntArray[x]);
                  for (int x= (int)DblArray[0]; x<DblArray[0]+30 ; x++)
                     System.out.println(DblArray[x]);
               
                  IntArray[0]=3;
                  utuDriver.utuC(IntArray, DblArray);
               }
               else 
               {
                  solve =false;
                  JOptionPane dialog = null;
                  dialog.showMessageDialog(panelShapeArea,"Not done the correct update");
                  mode = 0;
               }
               break;
            case 4:
               lengthi = IntArray[0];
               IntArray[0]=mode;
               System.out.println("main frame flag"+lengthi);
               lengthd = (int) DblArray[0];
               if(allConstraints.size()>oldConstraints.size())
               {
                  for(int i=0; i<allConstraints.size(); i++)
                     if(oldConstraints.indexOf(allConstraints.get(i)) == -1)
                     {
                        SKBaseConstraint con = allConstraints.get(i);
                        SKConstraintArray Cons =new SKConstraintArray(10);
                        if (Cons.indexOf(con)==-1)
                        {
                           Cons.add(con);
                           IntArray[lengthi++]=con.typeID;
                        //p.writeInt(con.typeID);
                           IntArray[lengthi++]=con.ID;
                        //p.writeInt(con.ID);
                           IntArray[lengthi++]=con.ShapeList.size();
                        //p.writeInt(con.ShapeList.size());
                           if (con instanceof SKNormalConstraint)
                           { //CAN consist of specific subshapes
                              for (int g=0; g<con.ShapeList.size(); g++)
                              {
                                 SKBaseShape tmp = con.ShapeList.get(g);
                                 IntArray[lengthi++]=tmp.ID;
                              //p.writeInt(tmp.ID);
                                 IntArray[lengthi++]=con.getConInfo(tmp,0);
                              //p.writeInt(con.getConInfo(tmp,0));
                              }
                           
                              switch (con.typeID)
                              {
                                 case 0: DblArray[lengthd++]=((SKDistanceConstraint)con).distance;
                                 //p.writeDouble( ((SKDistanceConstraint)con).distance );
                                    break;
                                 case 4: DblArray[lengthd++]=((SKAngleConstraint)con).angle;
                              //p.writeDouble( ((SKAngleConstraint)con).angle );
                              }
                           }
                           else
                           { //Cannot consist of specific subshapes
                              for (int g=0; g<con.ShapeList.size(); g++)
                                 IntArray[lengthi++]=con.ShapeList.get(g).ID;
                           //p.writeInt(con.ShapeList.get(g).ID);
                           }
                        }
                     }
               
                  IntArray[lengthi]=-1;
                  for (int x= IntArray[0]; x<IntArray[0]+30 ; x++)
                     System.out.println(IntArray[x]);
                  for (int x= (int)DblArray[0]; x<DblArray[0]+30 ; x++)
                     System.out.println(DblArray[x]);
               
                  IntArray[0]=4;
                  utuDriver.utuC(IntArray, DblArray);
               }
               else 
               {
                  solve =false;
                  JOptionPane dialog = null;
                  dialog.showMessageDialog(panelShapeArea,"Not done the correct update");
                  mode = 0;
               }
               break;
            case 5:
               lengthi = IntArray[0];
               IntArray[0]=mode;
               if(allConstraints.size()<oldConstraints.size())
               {
                  for(int i = 0 ; i<oldConstraints.size(); i++)
                     if(allConstraints.indexOf(oldConstraints.get(i)) == -1)
                        IntArray[lengthi++] = oldConstraints.get(i).ID;
                  for (int x= IntArray[0]; x<IntArray[0]+30 ; x++)
                     System.out.println(IntArray[x]);
                  for (int x= (int)DblArray[0]; x<DblArray[0]+30 ; x++)
                     System.out.println(DblArray[x]);
               
                  IntArray[0]=5;
                  utuDriver.utuC(IntArray, DblArray);
               }
               else 
               {
                  solve =false;
                  JOptionPane dialog = null;
                  dialog.showMessageDialog(panelShapeArea,"Not done the correct update");
                  mode = 0;
               }
               break;
            case 6:
               lengthi = IntArray[0];
               lengthd = (int)DblArray[0];
               IntArray[0]=mode;
               if(allGroups.size()>oldGroups.size())
               {
                  for(int i=0; i<allGroups.size(); i++)
                     if(oldGroups.indexOf(allGroups.get(i)) == -1)
                        allGroups.get(i).saveToUTUFile(lengthi,IntArray);
                  for (int x= IntArray[0]; x<IntArray[0]+30 ; x++)
                     System.out.println(IntArray[x]);
                  for (int x= (int)DblArray[0]; x<DblArray[0]+30 ; x++)
                     System.out.println(DblArray[x]);
               
                  IntArray[0]=6;
                  utuDriver.utuC(IntArray, DblArray);
               }
               else 
               {
                  solve =false;
                  JOptionPane dialog = null;
                  dialog.showMessageDialog(panelShapeArea,"Not done the correct update");
                  mode = 0;
               }
               break;
            case 7:
               lengthi = IntArray[0];
               lengthd = (int) DblArray[0];
               IntArray[0]=mode;
               if((allshapes.size()>oldShapes.size()) || (allConstraints.size()>oldConstraints.size()))
               {
                  if(allshapes.size()>oldShapes.size())
                  {
                     for(int i=0; i<allshapes.size(); i++)
                        if(oldShapes.indexOf(allshapes.get(i)) == -1)
                        {
                           SKBaseShape sh = allshapes.get(i);
                           switch(sh.ShapeTypeID)
                           {
                              case 3:
                                 SKLineShape line = (SKLineShape)sh;
                              //Write TypeID based if Segment,Ray,Line
                                 switch (line.pt1.pointType+line.pt2.pointType)
                                 {
                                    case 0:IntArray[lengthi++]=3; //Segment
                                    //System.out.println("typeID"+3);
                                       break;
                                    case 1: IntArray[lengthi++]=2; //Ray
                                       break;
                                    case 2: IntArray[lengthi++]=1; //Line (infinite)
                                 }
                                 break;
                              default:
                                 IntArray[lengthi++]=sh.ShapeTypeID;
                                 break;
                           }
                           IntArray[lengthi++]=sh.ID;	
                           switch (sh.ShapeTypeID)
                           {
                           
                              case 0:
                                 {
                                    DblArray[lengthd++]=(double)sh.getShapeX();
                                    DblArray[lengthd++]=(double)sh.getShapeY();
                                 }
                                 break;
                              case 3 : 
                                 { //SKLineShape
                                 //p.writeInt(888);
                                    SKLineShape line = (SKLineShape)sh;
                                    DblArray[lengthd++]=(double)line.pt1.getShapeX();
                                    DblArray[lengthd++]=(double)line.pt1.getShapeY();
                                    DblArray[lengthd++]=(double)line.pt2.getShapeX();
                                    DblArray[lengthd++]=(double)line.pt2.getShapeY();
                                    DblArray[lengthd++]=line.length;
                                 //p.writeInt(999);
                                 }
                                 break;
                              case 4 : 
                                 { //SKCircleShape
                                    DblArray[lengthd++]=(double)((SKCircleShape)sh).center.getShapeX();
                                    DblArray[lengthd++]=(double)((SKCircleShape)sh).center.getShapeY() ;
                                    DblArray[lengthd++]=((SKCircleShape)sh).radius ;
                                 }
                                 break;
                              case 5:
                                 {
                                    DblArray[lengthd++]=(double)((SKArcShape)sh).center.getShapeX();
                                    DblArray[lengthd++]=(double)((SKArcShape)sh).center.getShapeY();
                                    DblArray[lengthd++]=((SKArcShape)sh).radius;
                                    DblArray[lengthd++]=((SKArcShape)sh).angle;
                                 }
                                 break;
                              default: 
                                 break;
                           
                           }
                        }
                  
                     IntArray[lengthi++]=-1;
                     DblArray[lengthd++]=-1;
                  }
                  if(allConstraints.size()>oldConstraints.size())
                  {
                     for(int i=0; i<allConstraints.size(); i++)
                        if(oldConstraints.indexOf(allConstraints.get(i)) == -1)
                        {
                           SKBaseConstraint con = allConstraints.get(i);
                           SKConstraintArray Cons =new SKConstraintArray(10);
                           if (Cons.indexOf(con)==-1)
                           {
                              Cons.add(con);
                              IntArray[lengthi++]=con.typeID;
                           //p.writeInt(con.typeID);
                              IntArray[lengthi++]=con.ID;
                           //p.writeInt(con.ID);
                              IntArray[lengthi++]=con.ShapeList.size();
                           //p.writeInt(con.ShapeList.size());
                              if (con instanceof SKNormalConstraint)
                              { //CAN consist of specific subshapes
                                 for (int g=0; g<con.ShapeList.size(); g++)
                                 {
                                    SKBaseShape tmp = con.ShapeList.get(g);
                                    IntArray[lengthi++]=tmp.ID;
                                 //p.writeInt(tmp.ID);
                                    IntArray[lengthi++]=con.getConInfo(tmp,0);
                                 //p.writeInt(con.getConInfo(tmp,0));
                                 }
                              
                                 switch (con.typeID)
                                 {
                                    case 0: DblArray[lengthd++]=((SKDistanceConstraint)con).distance;
                                    //p.writeDouble( ((SKDistanceConstraint)con).distance );
                                       break;
                                    case 4: DblArray[lengthd++]=((SKAngleConstraint)con).angle;
                                 //p.writeDouble( ((SKAngleConstraint)con).angle );
                                 }
                              }
                              else
                              { //Cannot consist of specific subshapes
                                 for (int g=0; g<con.ShapeList.size(); g++)
                                    IntArray[lengthi++]=con.ShapeList.get(g).ID;
                              //p.writeInt(con.ShapeList.get(g).ID);
                              }
                           }
                        }
                     IntArray[lengthi++]=-1;
                     DblArray[lengthd++]=-1;
                  }
                  for (int x= IntArray[0]; x<IntArray[0]+30 ; x++)
                     System.out.println(IntArray[x]);
                  for (int x= (int)DblArray[0]; x<DblArray[0]+30 ; x++)
                     System.out.println(DblArray[x]);
               
                  IntArray[0]=7;
                  utuDriver.utuC(IntArray, DblArray);
               }
               else 
               {
                  solve =false;
                  JOptionPane dialog = null;
                  dialog.showMessageDialog(panelShapeArea,"Not done the correct update");
                  mode = 0;
               }
               break;
            default:
               break;
         }
         if(solve)
            while(SKUTUFile.waitForUpdate(IntArray, DblArray, SelectedShapes, this, panelShapeArea.getWidth(), panelShapeArea.getHeight(),dagMain ))
            {
               selectAllShapes();
               IntArray[0]=2;
               System.out.println("main frame3"+ IntArray[0]);
               utuDriver.utuC(IntArray, DblArray);
               System.out.println("utu");
               dataExists = true;
            }
      
         clearSelectedShapes(true,true);
         sbStatus.updatePanelText("Ready",0);
      }
   
   
      void scrHoriz_adjustmentValueChanged(AdjustmentEvent e)
      {
         doTranslate(lastHorizScroll-e.getValue(),0);
      }
      void scrVert_adjustmentValueChanged(AdjustmentEvent e)
      {
         doTranslate(0,lastVertScroll-e.getValue());
      }
      void scrHoriz1_adjustmentValueChanged(AdjustmentEvent e)
      {
         doTranslate1(lastHorizScroll1-e.getValue(),0);
      }
   
      void scrVert1_adjustmentValueChanged(AdjustmentEvent e)
      
      {
         doTranslate1(0,lastVertScroll1-e.getValue());
      }
      void tabpaneEditor_stateChanged(ChangeEvent e)
      {
         if (tabpaneEditor.getSelectedIndex() != 1 && SKOptions.byteOptions[ SKOptions.unSelGroupsWhen ] == 1)
         {
            clearSelectedGroups();
            RefreshShapeArea();
         }
      }
      void tabPartialSketch_stateChanged(ChangeEvent e)
      
      {
         if (tabPartialSketch.getSelectedIndex() != 1 )
         {
            clearSelectedGroups();
            RefreshShapeArea();
         }
      }
      void cmbScale_itemStateChanged(ItemEvent e)
      {
         if (e.getStateChange()==e.SELECTED)
            doScale( (Float.parseFloat(cmbScale.getSelectedItem().toString())) / 100 );
      }
   
      void treeConstraints_mouseReleased(MouseEvent e)
      {
         TreePath path = treeConstraints.getPathForLocation(e.getX(),e.getY());
         treeConstraints.setSelectionPath(path);
      
         if (e.isPopupTrigger() && path != null && path.getLastPathComponent() instanceof SKItemInterface)
            popTree.show(treeConstraints,e.getX(),e.getY());
      }
   
   ////////////////////////////////////////////////////////////////////////////
   //
   //      Methods related to major functionality
   //
   ////////////////////////////////////////////////////////////////////////////
   
   //Clears data
      public void NewData()
      {
         paracount=0;
      //Clear data & update displa;
         mniTest.setEnabled(true);
         mniAutoSolve.setEnabled(true);
         IntArray = new int[1];
         DblArray = new double[1];
         dataExists = false;
         if(dataExists)
            mniOptions.setEnabled(true);
         else 
            mniOptions.setEnabled(false);
         clearSelectedShapes(true,true);
         DrawnItems.clear();
         cmbShapes.removeAllItems();
         allGroups.clear();
         allshapes.clear();
         groupArray.clear();
         treeID=1;
         b1=false;
         update = false;
         for (int i=0; i<panelShapeArea.getComponentCount(); i++)
         {
            ((SKBaseShape)panelShapeArea.getComponent(i)).severTies();
            ((SKBaseShape)panelShapeArea.getComponent(i)).fixed=false;
         }
         panelShapeArea.removeAll();
         panelShapeArea.repaint();
      
      //Setup vars
         IDCnt = 1;
         ConstrIDCnt = 0;
         GroupIDCnt = 0;
         groupTree.severTies();
         sFileName = "";
         sbStatus.updatePanelText("Untitled",4);
         bDataModified = false;
         saveArrays = false;
         sbStatus.updatePanelText("",1);
         mouseOverItem = null;
      }
   
   //Lets user choose a file, and then calls LoadData(FileName)
      public void OpenFile()
      {
      //Let user find file
         JFileChooser dlgOpenFile = new JFileChooser(HomeDir);
      
         ExtensionFileFilter filter = new ExtensionFileFilter("Sketcher Data (*.skr)");
         filter.addExtension("skr");
      
         dlgOpenFile.setFileFilter(filter);
      
         int res = dlgOpenFile.showOpenDialog(this);
         if (res == JFileChooser.APPROVE_OPTION)
            LoadFile( dlgOpenFile.getSelectedFile().toString() ); //load the chosen file
      }
   
   //Actually loads data from the specified file
      public void LoadFile(String FileName)
      {
      //Make sure the file exists
         File fDataFile = new File(FileName);
         if (!fDataFile.isFile() || !fDataFile.exists()) 
            return;
      
         NewData();
      
      //Load the data
         try
         {
            readFromFile(FileName);
         }
            catch(Exception e)
            {
               JOptionPane.showMessageDialog(this,"File Error: An error occured while loading "+FileName,"File Error",JOptionPane.ERROR_MESSAGE);
               e.printStackTrace();
            }
      
         mruManager.addMRUItem(fDataFile.getName(), fDataFile.toString());
         mruManager.saveMRUtoFile(HomeDir+"/mruData.dat");
         mruManager.syncMenu(mniReopen);
      
      //Update vars
         sFileName = FileName;
         sbStatus.updatePanelText(sFileName,4);
         bDataModified = false;
         saveArrays = false;
         sbStatus.updatePanelText("",1);
      }
   
      public boolean InternalSaveAsChanges()
      {
      //First saveas, prompt for filename
         JFileChooser dlgSaveFile = new JFileChooser(HomeDir);
      
         int res = dlgSaveFile.showSaveDialog(this);
         if (res == JFileChooser.APPROVE_OPTION)
         {
            sFileName = dlgSaveFile.getSelectedFile().toString();
            sbStatus.updatePanelText(sFileName,4);
         
            //Create file
            try
            {
               saveToFile(sFileName);
            
            }
            
               catch(Exception e)
               
               {
                  JOptionPane.showMessageDialog(this,"File Error: An error occured while saving to "+sFileName,"File Error",JOptionPane.ERROR_MESSAGE);
                  e.printStackTrace();
               }
         }
         else
         {
            return false;
         }
         bDataModified = false;
         saveArrays = false;
         sbStatus.updatePanelText("",1);
         return true;
      }
   //Saves changes to data (returns false if canceled)
      public boolean InternalSaveChanges()
      {
      //Actually save data
         if (sFileName=="" || sFileName==null)
         {
         //First save, prompt for filename
            JFileChooser dlgSaveFile = new JFileChooser(HomeDir);
         
            int res = dlgSaveFile.showSaveDialog(this);
            if (res == JFileChooser.APPROVE_OPTION)
            {
               sFileName = dlgSaveFile.getSelectedFile().toString();
               sbStatus.updatePanelText(sFileName,4);
            
            //Create file
               try
               {
                  saveToFile(sFileName);
               
               }
                  catch(Exception e)
                  {
                     JOptionPane.showMessageDialog(this,"File Error: An error occured while saving to "+sFileName,"File Error",JOptionPane.ERROR_MESSAGE);
                     e.printStackTrace();
                  }
            }
            else
            {
               return false;
            }
         }
         else
         {
         //File exists, overwrite
            try
            {
               saveToFile(sFileName);
            }
               catch(Exception e)
               {
                  JOptionPane.showMessageDialog(this,"File Error: An error occured while saving to "+sFileName,"File Error",JOptionPane.ERROR_MESSAGE);
                  e.printStackTrace();
               }
         }
      
         bDataModified = false;
         saveArrays = false;
         sbStatus.updatePanelText("",1);
         return true;
      }
   
   // If changes have been made, prompts user to save (and saves if user wants)
   // Returns false if canceled, else returns true
      public boolean PromptSaveChanges()
      {
      //Has there been any changes?
         if (bDataModified)
         {
         //Prompt user to save
            int ret = JOptionPane.showConfirmDialog( this, "Data modified. Save changes?", "Save changes?" , JOptionPane.YES_NO_CANCEL_OPTION );
            if (ret == JOptionPane.CANCEL_OPTION) 
            { 
               return false;
            }
            if (ret == JOptionPane.YES_OPTION) InternalSaveChanges();
         }
      
         return true;
      }
      public boolean PromptSaveSketch()
      
      {
      //Is there additional data?
         if (dataExists)
         {
         //Prompt user to save sketch or sketch and data
            int ret = JOptionPane.showConfirmDialog( this, "Do you want to save solver data ?" , "Save Sketch Data?", JOptionPane.YES_NO_CANCEL_OPTION );
            if (ret == JOptionPane.CANCEL_OPTION) 
            {
               dataExists = false;
               saveArrays = false;
               int IntArray[];
               double DblArray[];
               if(bDataModified)
                  return PromptSaveChanges();
               else 
                  return false;
            }
            if (ret == JOptionPane.YES_OPTION)
            { saveArrays = true;
               InternalSaveChanges();}
         }
      
         return true;
      }
   
      public boolean PromptSaveAsSketch()
      
      
      {
      //Is there additional data?
         if (dataExists)
         {
         //Prompt user to save sketch or sketch and data
            int ret = JOptionPane.showConfirmDialog( this, "Do you want to save solver data ?" , "Save Sketch Data?", JOptionPane.YES_NO_CANCEL_OPTION );
            if (ret == JOptionPane.CANCEL_OPTION) 
            {
               dataExists = false;
               saveArrays = false;
               InternalSaveAs();
               return false;
            }
            if (ret == JOptionPane.YES_OPTION)
            { saveArrays = true;
               InternalSaveAs();}
         }
      
         return true;
      }
      public boolean InternalSaveAs()
      {
         String OldName = new String(sFileName);
      
         sFileName="";
         if ( !InternalSaveChanges() )
         { //Revert to old filename
            sFileName=OldName;
            return false;
         }
         else 
            return true;
      }
   
   //Copys a shape clone to the clipboard
      public void copySelectedShapes()
      {
         ClipShapes.clear();
         ClipWasCut = false;
      
      //Copy references in SelectedShapes to ClipShapes
         ClipShapes.setSize(SelectedShapes.size());
         SelectedShapes.copyArrayTo(ClipShapes);
      
      }
   
      public void cutSelectedShapes()
      {
         ClipShapes.clear();
         ClipWasCut = true;
         SKBaseShape sh;
         for (int i=0; i<SelectedShapes.size(); i++)
         {
            sh = SelectedShapes.get(i);
            ClipShapes.add(sh);
            for (int g=1; g<sh.getNumSubShapes()+1; g++)
            {panelShapeArea.remove(sh.getSubShape(g));
            
            }
         
         }
      
         clearSelectedShapes(true,true);
      }
   
      public void pasteClipShapes()
      {
         if (ClipWasCut)
         {
            SKBaseShape sh;
            for (int i=0; i<ClipShapes.size(); i++)
            {
               sh = ClipShapes.get(i);
               for (int g=1; g<sh.getNumSubShapes()+1; g++)
               {
                  panelShapeArea.add(sh.getSubShape(g));
                  sh.repaint();
               }
            
            }
         
            ClipWasCut = false; //can't just add them back anymore
         }
         else
         {
            for (int i=0; i<ClipShapes.size(); i++)
            {
            //ClipShapes.get(i).
            
               SKBaseShape l=ClipShapes.get(i);
            
               SKBaseShape r= createShape(l.getShapeTypeID(),l.getShapeX()+50,l.getShapeY()+50,IDCnt,true);
               if(r.ShapeTypeID==0) IDCnt++;
               else
                  IDCnt=IDCnt+r.getNumSubShapes()+1;
            }}
      }
   
   //Adds a shape to the selected list
      public void addSelectedShape(SKBaseShape newShape)
      {
         if (newShape.getSelectable().isSelected()) 
            return;
      
         newShape.getSelectable().selShape = newShape;
         newShape = newShape.getSelectable();
         SelectedShapes.add(newShape);
         newShape.setSelected(true);
      
         if (SelectedShapes.size()==1)
         {
            if (!editingShape)
            {
               editingShape = true;
               SelectedConstraint = null;
               tabpaneObjectProp.setTitleAt(1,"Constraints");
               tabpaneObjectProp.setSelectedIndex(0);
            }
         
            SelectedPropLevel=0;
            newShape.updateMainShapeData(vCurrentProps,vCurrentConstraints);
         
            Updating++;
            cmbShapes.setSelectedItem(newShape);
            Updating--;
         
            tableObjectProp.updateUI();
            treeConstraints.updateUI();
            treeConstraints.expandRow(1);
         
            tabpaneObjectProp.setEnabled(true);
         }
         else
         {
            if (SelectedPropLevel==0)
            {
               Updating++;
               cmbShapes.setSelectedIndex(-1);
               Updating--;
            }
         
            if (SelectedPropLevel<2)  mergeProps(newShape,1);
         
         }
         if (SelectedShapes.size() == 2)
         {
            Point pt1 = SelectedShapes.get(0).getPointForDistance(SelectedShapes.get(1)),
            pt2 = SelectedShapes.get(1).getPointForDistance(SelectedShapes.get(0));
            sbStatus.updatePanelText(Float.toString((float)pt1.distance(pt2)),2);
         }
         else sbStatus.updatePanelText("",2);
      
         updateConstraintUI();
      }
   
   //Clears all shapes, and adds newShape (so its the only selected shape)
      public void addOnlySelectedShape(SKBaseShape newShape)
      {
      //Already only selected shape?
         if (SelectedShapes.size()==1 && newShape.getSelectable().isSelected()) 
            return;
      
         clearSelectedShapes(false,true);
         addSelectedShape(newShape);
      }
   
   //Removes oldShape from the selected list
      public void removeSelectedShape(SKBaseShape oldShape)
      {
         oldShape = oldShape.getSelectable();
         SelectedShapes.removeShape(oldShape);
         oldShape.setSelected(false);
      
         if (SelectedShapes.size()==0)
         {
            cmbShapes.setSelectedIndex(-1);
            propFactory.freeProps(vCurrentProps);
            vCurrentConstraints.clear();
         
            tableObjectProp.removeEditor();
            tableObjectProp.updateUI();
            treeConstraints.updateUI();
            treeConstraints.collapseRow(1);
         
            tabpaneObjectProp.setEnabled(false);
         }
      
         if (SelectedShapes.size() == 2)
         {
            Point pt1 = SelectedShapes.get(0).getPointForDistance(SelectedShapes.get(1)),
            pt2 = SelectedShapes.get(1).getPointForDistance(SelectedShapes.get(0));
            sbStatus.updatePanelText(Float.toString((float)pt1.distance(pt2)),2);
         }
         else sbStatus.updatePanelText("",2);
      
         mergeProps(null,2);
         updateConstraintUI();
      }
   
      public void toggleSelectedShape(SKBaseShape sh)
      {
         if (sh.getSelectable().isSelected())
            removeSelectedShape(sh);
         else
            addSelectedShape(sh);
      }
   
   //Removes all shapes from the selected list (updateView should be true!)
      public void clearSelectedShapes(boolean updateView, boolean unselectShapes)
      {
         if (unselectShapes)
         {
            SKBaseShape sh;
            for (int i=0; i<SelectedShapes.size(); i++)
            {
               sh=SelectedShapes.get(i);
               sh.setSelected(false);
            }
         }
      
         SelectedShapes.clear();
         tableObjectProp.removeEditor();
         tabpaneObjectProp.setSelectedIndex(0);
      
         if (updateView)
         {
            propFactory.freeProps(vCurrentProps);
            vCurrentConstraints.clear();
         
            cmbShapes.setSelectedIndex(-1);
            tableObjectProp.updateUI();
            treeConstraints.updateUI();
            treeConstraints.collapseRow(1);
         
            tabpaneObjectProp.setEnabled(false);
         
            updateConstraintUI();
         }
      
         if (!editingShape && SelectedConstraint != null)
         {SelectedConstraint.repaint();
            SelectedConstraint = null;
         }
      
         sbStatus.updatePanelText("",2);
      }
   
   //Selects all shapes
      public void selectAllShapes()
      {
         for (int i=0; i<panelShapeArea.getComponentCount(); i++)
            addSelectedShape( ((SKBaseShape)panelShapeArea.getComponent(i)) );
      }
   
   //Merges properties of currently selected shapes with new selected shape(s)
      public void mergeProps(SKBaseShape newShape, int newPropLevel)
      {
      //Adjust SelectedPropLevel
         if (SelectedShapes.size()==1)
         { //Only 1 selected shape
            newPropLevel=0;
            cmbShapes.setSelectedItem(SelectedShapes.get(0));
         }
         else
         {
            if (newShape==null)
            { //Removed shape
               newPropLevel = 1;
               if (SelectedPropLevel==2)
               { //Are they now all the same class?
                  Class base = SelectedShapes.get(0).getClass();
                  int i=0;
                  while (newPropLevel==1 && i<SelectedShapes.size())
                  {
                     if (SelectedShapes.get(i).getClass() != base)  newPropLevel=2;
                     i++;
                  }
               }
            }
            else
            { //Added shape
               if (newPropLevel==1 && SelectedShapes.get(0).getClass() != newShape.getClass())  newPropLevel=2;
            }
         }
      
      //Don't do anything if there is no change
         if (newPropLevel==SelectedPropLevel) 
            return;
      
      //Refresh props
         if (newPropLevel<SelectedPropLevel)  SelectedShapes.get(0).updateMainShapeData(vCurrentProps,null);
      
      //Remove props < SelectedPropLevel
         int i=0;
         while ( i<vCurrentProps.size() )
            if (vCurrentProps.getShapePropType(i)<newPropLevel)
               vCurrentProps.remove(i);
            else
               i++;
      
         SelectedPropLevel=newPropLevel;
         tableObjectProp.repaint();
      }
   
      void updateConstraintUI()
      {
         if(update && (!((mode==4) || (mode==7)) ) )
         {
            btnAngleConstraint.setEnabled( false);
            btnDistanceConstraint.setEnabled( false );
            btnIncidenceConstraint.setEnabled( false );
            btnParallelConstraint.setEnabled( false );
            btnPerpConstraint.setEnabled( false );
            btnTangentConstraint.setEnabled( false );
         
            mniAngleConstr.setEnabled( false );
            mniDistanceConstr.setEnabled( false);
            mniIncidenceConstr.setEnabled( false );
            mniParallelConstraint.setEnabled( false );
            mniPerpConstraint.setEnabled( false);
            mniTangentConstraint.setEnabled( false );
         }
         else
         {
            if (SelectedShapes.size() == 2)
            { //Enable and show as appropriate
               btnAngleConstraint.setEnabled( SKAngleConstraint.isAvailable(SelectedShapes) );
               btnDistanceConstraint.setEnabled( SKDistanceConstraint.isAvailable(SelectedShapes) );
               btnIncidenceConstraint.setEnabled( SKIncidenceConstraint.isAvailable(SelectedShapes) );
               btnParallelConstraint.setEnabled( SKParallelConstraint.isAvailable(SelectedShapes) );
               btnPerpConstraint.setEnabled( SKPerpConstraint.isAvailable(SelectedShapes) );
               btnTangentConstraint.setEnabled( SKTangentConstraint.isAvailable(SelectedShapes) );
            
               mniAngleConstr.setEnabled( SKAngleConstraint.isAvailable(SelectedShapes) );
               mniDistanceConstr.setEnabled( SKDistanceConstraint.isAvailable(SelectedShapes) );
               mniIncidenceConstr.setEnabled( SKIncidenceConstraint.isAvailable(SelectedShapes) );
               mniParallelConstraint.setEnabled( SKParallelConstraint.isAvailable(SelectedShapes) );
               mniPerpConstraint.setEnabled( SKPerpConstraint.isAvailable(SelectedShapes) );
               mniTangentConstraint.setEnabled( SKTangentConstraint.isAvailable(SelectedShapes) );
            }
            else
            { //Disable and hide all
               btnAngleConstraint.setEnabled(false);
               btnDistanceConstraint.setEnabled(false);
               btnIncidenceConstraint.setEnabled(false);
               btnParallelConstraint.setEnabled(false);
               btnPerpConstraint.setEnabled(false);
               btnTangentConstraint.setEnabled(false);
            
               mniAngleConstr.setEnabled(false);
               mniDistanceConstr.setEnabled(false);
               mniIncidenceConstr.setEnabled(false);
               mniParallelConstraint.setEnabled(false);
               mniPerpConstraint.setEnabled(false);
               mniTangentConstraint.setEnabled(false);
            }
         }
      }
   
      void panelShapeArea_mouseClicked(MouseEvent e)
      {//System.out.println(e.getID());
      
         if (e.isPopupTrigger())
         {
         //popupShape.show(this,e.getX(),e.getY());
            return;
         }
      
         if (mouseOverItem != null)
         {
            if (mouseOverItem instanceof SKBaseShape)
            {
               if (e.isControlDown())
                  toggleSelectedShape((SKBaseShape)mouseOverItem);
               else
               {addOnlySelectedShape((SKBaseShape)mouseOverItem);
                  //System.out.println("shape"+SelectedShapes.get(0));
               }
            }
            else
               EditConstraint( (SKBaseConstraint)mouseOverItem );
         
            return;
         }
      
         if (btnCursor.isSelected())
         {
            clearSelectedShapes(true,true);
            return;
         }
      
      //A shape is selected, add to data and create
         if (!bDataModified)
         {
            bDataModified=true;
            sbStatus.updatePanelText("Modified",1);
         }
      
      //Create a shape based on which shape button is pressed
         int shapeTypeID=0;
         switch (bgShapes.getSelectedIndex())
         {
            case 1:
               shapeTypeID =0;
               break;
            case 2:
               shapeTypeID =3;
               break;
            case 3:
               shapeTypeID =4;
               break;
            case 4:
               shapeTypeID =5;
               break;
            case 11:
               shapeTypeID =6;
               break;
            default: 
               break;
         
         }
         if(shapeTypeID==0) {createShape(shapeTypeID, e.getX(), e.getY(), IDCnt, true);
            IDCnt++;
         }
         else
            IDCnt = IDCnt+(createShape(shapeTypeID, e.getX(), e.getY(), IDCnt, true)).getNumSubShapes()+1;
      }
   
      void panelGroups_mouseClicked(MouseEvent e)
      
      {
         if (e.isPopupTrigger())
         {
            return;
         }
      
      }
      public SKBaseShape createShape(int shapeTypeID,int x, int y, int ID, boolean autoSelect)
      {  SKBaseShape newShape = null;
         boolean ResetCursorButton = true;
         boolean RedrawAll = false;
         boolean AddToShapeArea = true;
      
         switch (shapeTypeID)
         {
            case 0:
               {  newShape = new SKPointShape(this,ID,x,y);
                  panelShapeArea.add((SKPointShape)newShape);
                  allshapes.add(newShape);
               }
               break;
            case 1:
               break;
            case 2:
               break;
            case 3:  
               { //Line segment
                  newShape = new SKLineShape(this,ID,x,y);
                  allshapes.add(newShape);
               //Add points to ShapeArea
                  panelShapeArea.add( ((SKLineShape)newShape).pt1 );
                  panelShapeArea.add( ((SKLineShape)newShape).pt2 );
                  AddToShapeArea = false;
                  ResetCursorButton = true;
                  RedrawAll = true;
               }
               break;
            case 4:  
               { //Circle Shape
                  newShape = new SKCircleShape(this,ID,x,y);
                  allshapes.add(newShape);
                 //Add point to ShapeArea
                  panelShapeArea.add( ((SKCircleShape)newShape).center );
               
                  AddToShapeArea = false;
                  ResetCursorButton = true;
                  RedrawAll = true;
               }
               break;
            case 5:  
               { //Arc Shape
                  newShape = new SKArcShape(this,ID,x,y);
                  allshapes.add(newShape);
                  panelShapeArea.add( ((SKArcShape)newShape).center );
                  panelShapeArea.add( ((SKArcShape)newShape).pt1 );
                  panelShapeArea.add( ((SKArcShape)newShape).pt2 );
               
                  AddToShapeArea = false;
                  ResetCursorButton = true;
                  RedrawAll = true;
               }
            /* 4-Ray, 5-Line (infinite) */
               break;
            case 6:  newShape = new SKImageShape(this,ID,x,y);
               allshapes.add(newShape);
               break;
            default: 
               return null; //Unknown shape
         }
         DrawnItems.add( newShape.getSelectable() );
      
         if (AddToShapeArea) panelShapeArea.add(newShape);
         Updating++;
         cmbShapes.addItem( newShape );
         cmbShapes.setSelectedItem( newShape );
         Updating--;
      
         if (ResetCursorButton)
         {
            btnCursor.setSelected(true);
            panelShapeArea.setCursor( Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
         }
      
         if (autoSelect)  addOnlySelectedShape(newShape);
      
         if (RedrawAll)
            panelShapeArea.repaint();
         else
            newShape.repaint();
         //System.out.println(panelShapeArea.getComponentCount());
      // allshapes.add(newShape);
         return newShape;
      }
   
      SKBaseConstraint canCreate(int conTypeID, SKShapeArray selectedShapes)
      {
         if (selectedShapes.size() == 0)  
            return null;
      
         SKBaseShape sh = selectedShapes.get(0);
         SKBaseConstraint con;
         for (int i=0; i<sh.ConstraintList.size(); i++)
         {
            con = sh.ConstraintList.get(i);
            if (con.typeID==conTypeID)
            {
               if (con.constrEquals(SelectedShapes,con))
                  return con;
            }
            else
            {
               switch (conTypeID)
               {
                  case 3 : 
                     { //Perp -- same as Tangent (5)
                        if (con instanceof SKParallelConstraint)
                           return con;
                     }
                     break;
                  case 4 : 
                     {//Parallel -- same as Tangent (5)
                        if (con instanceof SKPerpConstraint)
                           return con;
                     }
               }
            }
         }
      
         return null;
      }
   
      public SKBaseConstraint createConstraint(int typeID, int ID)
      {
         SKBaseConstraint con    = null,
         oldCon = canCreate(typeID,SelectedShapes);
      
         if (oldCon==null)
            switch (typeID)
            {
               case 1 : con = new SKIncidenceConstraint(this,ID);
                  break;
               case 2 : con = new SKPerpConstraint(this,ID);
                  break;
               case 3 : con = new SKParallelConstraint(this,ID);
                  break;
               case 4 : con = new SKAngleConstraint(this,ID);
                  break;
               case 5 : con = new SKTangentConstraint(this,ID);
                  break;
               default: con = new SKDistanceConstraint(this,ID); //
            }
         allConstraints.add(con);
         btnCursor.setSelected(true);
         panelShapeArea.setCursor( Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
      
         if (con == null)
         { //Constraint already existed, tell user and edit it
            if (oldCon.typeID==typeID)
               JOptionPane.showMessageDialog(this,"Constraint Error: This constraint already exists.","Constraint Error",JOptionPane.ERROR_MESSAGE);
            else
               JOptionPane.showMessageDialog(this,"Constraint Error: There is a conflicting constraint.","Constraint Error",JOptionPane.ERROR_MESSAGE);
         
            EditConstraint(oldCon);
         }
         else
         { //Added new constraint
            DrawnItems.add(con);
         }
      
         return con;
      }
   
   //Allows an easy way for shapes to cause panelShapeArea to repaint
      public void RefreshShapeArea()
      {
         panelShapeArea.repaint();
      }
   
      public void RefreshShapeArea(int x,int y,int w,int h)
      {
         panelShapeArea.repaint(x,y,w,h);
      }
   
      public void RefreshShapeArea(Rectangle rt)
      {
         panelShapeArea.repaint(rt);
      }
   
      public void EditConstraint(SKBaseConstraint con)
      {
         editingShape = false;
         clearSelectedShapes(false,true);
         vCurrentConstraints.clear();
         tabpaneObjectProp.setTitleAt(1,"Shapes");
         tabpaneObjectProp.setEnabled(true);
         tabpaneObjectProp.setSelectedIndex(0);
      
         SelectedConstraint = con;
         SelectedConstraint.updateMainProperties(vCurrentProps);
         SelectedConstraint.repaint();
      
         cmbShapes.setSelectedIndex(-1);
         tableObjectProp.removeEditor();
         tableObjectProp.updateUI();
         treeConstraints.updateUI();
         deleteConstraint = con;
      }
   
      public void InitConstraint(SKBaseConstraint con, int minShapes, int maxShapes)
      {
         if (con == null) 
            return;
         //System.out.println("main: "+con);
         if (minShapes == -1 || SelectedShapes.size()>=minShapes && SelectedShapes.size()<=maxShapes)
         {
            for (int i=0; i<SelectedShapes.size(); i++)
            {
               con.addShape( SelectedShapes.get(i) );
            }
            if(con instanceof SKParallelConstraint)
            {
               for(int i=0; i<con.ShapeList.size(); i++)
                  for(int j=0; j<con.ShapeList.get(i).ConstraintList.size(); j++)
                     if(con.ShapeList.get(i).ConstraintList.get(j) instanceof SKParallelConstraint)
                        ((SKParallelConstraint)con).count=((SKParallelConstraint)con.ShapeList.get(i).ConstraintList.get(j)).count ;
               if(((SKParallelConstraint)con).count == 0)
               {paracount++;
                  ((SKParallelConstraint)con).count = paracount;}
            }
            if (SKOptions.byteOptions[ SKOptions.simpleSolverVersion ] == 0)
               con.isDrawn = (SKOptions.byteOptions[ SKOptions.simpleSolverMode ]>0 && !SKSimpleSolver.DoSimpleSolver(con,null,new SKConstraintArray(3)));
            else 
               con.isDrawn = (SKOptions.byteOptions[ SKOptions.simpleSolverMode ]>0 && !SKSimpleSolver.DoSimpleSolver(con));
         
            repaint();
         
            EditConstraint(con);
         }
         else
         {
            if (minShapes==maxShapes)
               JOptionPane.showMessageDialog(this,"Incorrect number of items selected. Allowed: "+minShapes,"Constraint Error",JOptionPane.ERROR_MESSAGE);
            else
               JOptionPane.showMessageDialog(this,"Incorrect number of items selected. Allowed: ["+minShapes+","+maxShapes+"]","Constraint Error",JOptionPane.ERROR_MESSAGE);
         }
      }
   
      public void saveToFile(String fileName) throws FileNotFoundException,IOException
      {
         sbStatus.updatePanelText("Writing...",0);
         setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
      
         FileOutputStream fStream = new FileOutputStream(fileName);
         DataOutputStream p = new DataOutputStream(fStream);
      
      //Write header
         p.writeInt(IDCnt);
         p.writeInt(ConstrIDCnt);
      
      //Loop through shapes and write to file
         SKBaseShape sh;
         for (int i=0; i<panelShapeArea.getComponentCount(); i++)
         {
            sh = (SKBaseShape)panelShapeArea.getComponent(i);
            if ( sh.getSelectable().isPrimaryShape(sh) )
               sh.getSelectable().writeToStream(p,true);
         }
         System.out.println("saved shapes");
      //Signify end of shapes
         p.writeInt(-2);
      
      //Loop through and write constraints to file
         for (int i=0; i<panelShapeArea.getComponentCount(); i++)
         {
            sh = (SKBaseShape)panelShapeArea.getComponent(i);
            if ( sh.getSelectable().isPrimaryShape(sh) )
               sh.getSelectable().writeConstraintsToStream(p,true,null);
         }
         System.out.println("saved constraints");
      //Signify end of constraints
         p.writeInt(-2);
      
      //Write group to tree
         groupTree.saveToStream(p);
         System.out.println("saved trees");
      //Signify end of tree
         p.writeInt(-2);
      
      //Write arrays
         if(saveArrays)
         {
            p.writeInt(1);
            p.writeInt(IntArray.length);
            for(int i=0; i<IntArray.length; i++)
               p.writeInt(IntArray[i]);
            p.writeInt(DblArray.length);	
            for(int i=0; i<DblArray.length; i++)
               p.writeDouble(DblArray[i]);
            System.out.println("saved arrays");
         }
      //Signify end of file
         p.writeInt(-3);
      //Flush to file
         p.flush();
         p.close();
         fStream.flush();
         fStream.close();
      
         sbStatus.updatePanelText("Ready",0);
         setCursor( Cursor.getDefaultCursor() );
      }
   
      public void readFromFile(String fileName) throws FileNotFoundException,IOException,StreamCorruptedException
      {
         FileInputStream fStream = new FileInputStream(fileName);
      
         readFromStream(fStream,true);
      
         fStream.close();
      }
   
      public void readFromStream(InputStream strm, boolean readID) throws IOException,StreamCorruptedException
      {
         sbStatus.updatePanelText("Reading...",0);
         setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
      
      //FileInputStream fStream = new FileInputStream(fileName);
         DataInputStream p = new DataInputStream(strm);
      
      //Read header
         if (readID)
         {
            IDCnt = p.readInt();
            ConstrIDCnt = p.readInt();
         }
         System.out.println("reading ... ");
      //Read shapes
         SKBaseShape sh;
         int typeID = p.readInt(),
         newID;
         while (typeID != -2)
         {
            newID = (readID) ? p.readInt() : IDCnt;
            sh = createShape(typeID,1,1,newID,false);
            if (!readID) IDCnt += sh.getNumSubShapes()+1;
            sh.readFromStream(p);
            typeID = p.readInt();
            System.out.println("read shapes"+readID+" "+newID);
         }
         System.out.println("read shapes");
      //Read constraints
         SKBaseConstraint con;
         int g,h,i,j;
         typeID = p.readInt();
         while (typeID != -2)
         {
            newID = (readID) ? p.readInt() : ConstrIDCnt++;
            con = createConstraint(typeID,newID);
            con.readFromStream(p);
         
         //associate shapes
            g = p.readInt();
            int[] IDlist = new int[g];
            for (h=0; h<g; h++)
               IDlist[h] = p.readInt();
         
            j=0;
            while (j<g)
            {
               h=0;
               while (/* h<panelShapeArea.getComponentCount() && */ ((SKBaseShape)panelShapeArea.getComponent(h)).getSelectable().ID != IDlist[j])
                  h++;
               con.addShape( ((SKBaseShape)panelShapeArea.getComponent(h)).getSelectable() );
            
               j++;
            }
         
            typeID = p.readInt();
         }
         System.out.println("read constraints");
      //Read groups from stream
         groupTree.readFromStream(p,panelShapeArea);
         GroupIDCnt = groupTree.findMaxID(0);
      
         treeGroups.updateUI();
      //check if additional data exists
         p.readInt();
         System.out.println("read trees");
         if(p.readInt()!=-3)
         {
            int print = p.readInt();
            if(print == 1)
            {
               IntArray = new int[p.readInt()];
               dataExists = true;
               mniOptions.setEnabled(true);
               for( i=0; i<IntArray.length; i++)
                  IntArray[i] = p.readInt();
               DblArray = new double[p.readInt()];
               for( i=0; i<DblArray.length; i++)
                  DblArray[i]=p.readDouble();
               System.out.println("read arrays");
            }
         }
         else
         {
            dataExists = false;
            mniOptions.setEnabled(false);
            mniReStart.setEnabled(false);
            int IntArray[];
            double DblArray[];
         }
      //Reset other vars
         mouseOverItem = null;
         System.out.println("read ");
         sbStatus.updatePanelText("Ready",0);
         setCursor( Cursor.getDefaultCursor() );
      }
   
      public void addUserObject(String fileName, String objectName) throws FileNotFoundException,IOException
      {
         sbStatus.updatePanelText("Writing...",0);
         setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
      
         File tmpFile = new File(repository.repositoryDir+"tmpfile.$$$");
         File zipFile = new File(fileName);
      
         FileOutputStream fStream = new FileOutputStream(tmpFile);
         ZipOutputStream zip = new ZipOutputStream(fStream);
      
         InputStream ins;
         ZipFile zin = new ZipFile(zipFile);
         Enumeration entries = zin.entries();
         byte[] buf = new byte[1024];
         int len;
         ZipEntry zEntry;
         while (entries.hasMoreElements())
         { //write current entries into new file
            zEntry = (ZipEntry)entries.nextElement();
            if (zEntry.getName() != "~Default")
            {
               zip.putNextEntry( new ZipEntry( zEntry.getName() ) );
               ins = zin.getInputStream( zEntry );
            
               do
               {
                  len = ins.read(buf);
                  if (len>-1) zip.write(buf,0,len);
               }
               while (len!=-1);
            
               ins.close();
               zip.closeEntry();
            }
         }
      
         zEntry = null;
         ins = null;
         zin.close();
         zin = null;
      
         zip.putNextEntry( new ZipEntry(objectName) );
      
         DataOutputStream p = new DataOutputStream(zip);
      
      //Loop through shapes and write to file
         SKBaseShape sh ;
         for (int i=0; i<SelectedShapes.size(); i++)
         {SelectedShapes.get(i).writeToStream(p,false);
         }
      
      //Signify end of shapes
         p.writeInt(-2);
      
      //Loop through and write constraints to file
         for (int i=0; i<SelectedShapes.size(); i++)
            SelectedShapes.get(i).writeConstraintsToStream(p,false,SelectedShapes);
      
      //Signify end of file
         p.writeInt(-2);
         p.writeInt(-2);
         p.writeInt(-3);
      
      //Flush to file
         p.flush();
         zip.flush();
         zip.close();
         fStream.flush();
         fStream.close();
      
      //Delete old file, replace it with new file
         boolean res;
         res = zipFile.delete();
         res = tmpFile.renameTo(zipFile);
      
         sbStatus.updatePanelText("Ready",0);
         setCursor( Cursor.getDefaultCursor() );
      }
   
      public void clearSelectedGroups()
      {
         drawGroupRects = false;
      
         selectedGroups.clear();
      
         for(int i=0; i<selectedGroupShapes.size(); i++)
            selectedGroupShapes.get(i).highestGroup = null;
      
         selectedGroupShapes.clear();
      
         treeGroups.setSelectionPath(null);
      }
   
      public void doScale(float newScale)
      {
         float delta = newScale/lastScale;
         Component cmp;
         for(int i=0; i<panelShapeArea.getComponentCount(); i++)
         {
            cmp = panelShapeArea.getComponent(i);
            cmp.setLocation( java.lang.Math.round(cmp.getX()*delta), java.lang.Math.round(cmp.getY()*delta) );
         }
         lastScale = newScale;
      }
      public void doTranslate(int deltaX, int deltaY)
      {
         Component cmp;
         for (int i=0; i<panelShapeArea.getComponentCount(); i++)
         {
            cmp = panelShapeArea.getComponent(i);
            cmp.setLocation(cmp.getX()+deltaX,cmp.getY()+deltaY);
         }
         panelShapeArea.repaint();
      
         if (deltaX != 0)  lastHorizScroll = lastHorizScroll - deltaX;
         if (deltaY != 0)  lastVertScroll = lastVertScroll - deltaY;
      }
      public void doTranslate1(int deltaX, int deltaY)
      
      {   
         Component cmp;
         for (int i=0; i<panelPartial.getComponentCount(); i++)
         {
            cmp = panelPartial.getComponent(i);
            cmp.setLocation(cmp.getX()+deltaX,cmp.getY()+deltaY);
         }
         panelPartial.repaint();
         if (deltaX != 0)  lastHorizScroll1 = lastHorizScroll1 - deltaX;
         if (deltaY != 0)  lastVertScroll1 = lastVertScroll1 - deltaY;
      }
   
      public void deleteItem(Object item)
      {//System.out.println("entered deleted item");
         if (item instanceof SKBaseConstraint)
         {
            SKBaseConstraint con = (SKBaseConstraint)item;
            if (SelectedConstraint == con)
               SelectedConstraint = null;
            if(vCurrentConstraints.size()>0)
               vCurrentConstraints.remove(con);
            allConstraints.remove(con);
            con.severTies();
            con = null;
         
            treeConstraints.updateUI();
            tableObjectProp.updateUI();
            RefreshShapeArea();
         }
         else if (item instanceof SKBaseShape)
         {
            SKBaseShape sh = (SKBaseShape)item;
            cmbShapes.removeItem(sh);
         
            SKGroups.removeShapeFromGroup(sh,groupTree,selectedGroups);
            selectedGroupShapes.removeShape(sh);
            treeGroups.updateUI();
            lstGroupShapes.updateUI();
            for (int g=1; g<sh.getNumSubShapes()+1; g++)
            {
               panelShapeArea.remove(sh.getSubShape(g));
               sh.getSubShape(g).severTies();
            }
         
            if(sh.getNumSubShapes()!=1)sh.severTies();
         
            DrawnItems.remove(sh);
            sh = null;
            RefreshShapeArea();
         
         }
      
         mouseOverItem = null;
      
      }
      public void paint(Graphics g)
      {
         super.paint(g);
         if(tabPartialSketch.getSelectedIndex()==0)
            panelShapeArea.drawSt(g,solvingShapes);
         else
            panelPartial.drawNames(g);
         return;
      }
      void drawSt( Graphics g)
      {Color red = Color.red;
      
         for (int j=0; j<panelShapeArea.getComponentCount(); j++)
         {  //if(solvingShapes.get(i).ID==((SKBaseShape)panelShapeArea.getComponent(j)).ID);
            SKBaseShape solveShape = (SKBaseShape) panelShapeArea.getComponent(j);
            if(solvingShapes.findByID(solveShape.ID) !=null)
            {
               g.setColor(red);
               g.drawString(solveShape.Name.charAt(0)+""+solveShape.ID, solveShape.getShapeX()+20, solveShape.getShapeY()+80);
            }
         }
      }
   
   }