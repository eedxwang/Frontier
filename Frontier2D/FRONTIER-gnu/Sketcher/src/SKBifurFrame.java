/**
 * Title:        SKBifurFrame
 * Description:  Frame used to display bifurcations
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
   import java.awt.*;
   import java.awt.event.*;
   import java.awt.Graphics;
   import java.awt.geom.*;

   public class SKBifurFrame extends JDialog
   {
      public SKMainFrame frameMain;
      public int currBifurcation = -1;
      public int lastHorizScroll = 0,lastVertScroll = 0;
      public int horvalue = 0;
      public int vervalue = 0;
      public float              lastScale = 1;
      public boolean approved = true;
      public SKBifurcationArray bifurcations;
      public SKShapeArray Shapes = new SKShapeArray(2);
      public JButton btnPrevious = new JButton();
      public JButton btnNext = new JButton();
      public JButton btnConfirm = new JButton();
      public JButton btnCancel = new JButton();
      public JLabel lblStatus = new JLabel();
      public JLabel lblScale = new JLabel();
      public JPanel pnlBifurButtons = new JPanel();
      public JPanel pnlMain = new JPanel();
      public JPanel pnlControls = new JPanel();
      public JPanel pnlButtons = new JPanel();
      public JPanel pnlConfirm = new JPanel();
      public JSelectionPanel pnlShapes = new JSelectionPanel(frameMain);
      public JScrollBar scrVert = new JScrollBar();
      public JScrollBar scrHoriz = new JScrollBar();
      public JComboBox cmbScale = new JComboBox();
      public BorderLayout borderLayout1 = new BorderLayout();
      public BorderLayout borderLayout2 = new BorderLayout();
   
      public SKBifurFrame(Frame owner, String title, boolean modal)
      {
         super(owner,title,modal);
         frameMain = (SKMainFrame)owner;
         pnlShapes = new JSelectionPanel(frameMain);
         try
         {
            jbInit();
            setSize(600,490);
         //Center on desktop
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension d = getSize();
            int x = (screen.width - d.width) >> 1;
            int y = (screen.height - d.height) >> 1;
           // setLocation(x, y);
         }
            catch(Exception e)
            {
               e.printStackTrace();
            }
      
      }
   
   
      private void jbInit() throws Exception
      {
         pnlMain.setLayout(borderLayout1);
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
         btnPrevious.setText("Previous");
         btnPrevious.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       btnPrevious_actionPerformed(e);
                                    }
                                 });
         lblStatus.setText(" Bifurcation 1 / N ");
         btnNext.setText("Next");
         btnNext.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       btnNext_actionPerformed(e);
                                    }
                                 });
      
         pnlShapes.setLayout(null);
         cmbScale.setEditable(true);
         cmbScale.addItemListener(
                                 new java.awt.event.ItemListener()
                                 {
                                 
                                    public void itemStateChanged(ItemEvent e)
                                    {
                                       cmbScale_itemStateChanged(e);
                                    }
                                 });
         lblScale.setText("Scale");
         pnlMain.setBorder(BorderFactory.createLoweredBevelBorder());
         pnlButtons.setLayout(borderLayout2);
         btnConfirm.setText("Select Bifurcations");
         btnConfirm.addActionListener(
                                 new java.awt.event.ActionListener()
                                 {
                                 
                                    public void actionPerformed(ActionEvent e)
                                    {
                                       btnConfirm_actionPerformed(e);
                                    }
                                 });
         btnCancel.setText("Cancel");
         btnCancel.addActionListener(
                              
                                 new java.awt.event.ActionListener()
                                 
                                 {
                                 
                                 
                                    public void actionPerformed(ActionEvent e)
                                    
                                    {
                                       btnCancel_actionPerformed(e);
                                    }
                                 });
      
         this.addWindowListener(
                                 new java.awt.event.WindowAdapter()
                                 {
                                 
                                    public void windowOpened(WindowEvent e)
                                    {
                                       this_windowOpened(e);
                                    }
                                 });
         pnlBifurButtons.add(btnPrevious, null);
         pnlBifurButtons.add(lblStatus, null);
         pnlBifurButtons.add(btnNext, null);
         pnlButtons.add(pnlConfirm, BorderLayout.SOUTH);
         pnlConfirm.add(btnConfirm, null);
         pnlConfirm.add(btnCancel, null);
         this.getContentPane().add(pnlMain, BorderLayout.CENTER);
         pnlMain.add(pnlShapes, BorderLayout.CENTER);
         pnlMain.add(scrVert, BorderLayout.EAST);
         pnlMain.add(scrHoriz, BorderLayout.SOUTH);
         this.getContentPane().add(pnlControls, BorderLayout.NORTH);
         pnlControls.add(lblScale, null);
         pnlControls.add(cmbScale, null);
         pnlButtons.add(pnlBifurButtons, BorderLayout.CENTER);
         this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);
      
         scrHoriz.setValues(0,pnlShapes.getWidth(),-1000,1000);
         scrHoriz.setUnitIncrement(10);
         scrVert.setValues(0,pnlShapes.getHeight(),-1000,1000);
         scrVert.setUnitIncrement(10);
      
         for(int i=0; i<frameMain.scaleValues.length; i++)
            cmbScale.addItem(frameMain.scaleValues[i]);
         cmbScale.setSelectedIndex(1);
      
      }
   
      void this_windowOpened(WindowEvent e)
      
      {         
         setBifurcation(0);
      
         boolean yes=false; 
         SKBaseShape fix = null;
         SKBaseShape fix1 = null; 
         int count =0;
         JOptionPane dialog = null;
         for(int i=0; i<pnlShapes.getComponentCount();i++)
         {
            SKBaseShape sh = (SKBaseShape)pnlShapes.getComponent(i);
            if((sh instanceof SKPointSubShape) && (Shapes.indexOf(sh.getSelectable())==-1))
               Shapes.add(sh.getSelectable());
            else if (!(sh instanceof SKPointSubShape))
               Shapes.add(sh);
         }
         for(int i=0; i<pnlShapes.getComponentCount();i++)
         {
            SKBaseShape try1 = (SKBaseShape)pnlShapes.getComponent(i);
            int id = try1.ID;
            if(!(try1 instanceof SKPointSubShape))
            {
               SKBaseShape try2 = frameMain.allshapes.findByID(id);
               if(try2 instanceof SKCircleShape) ((SKCircleShape)try2).bifur=true;
               if(try2 instanceof SKArcShape) ((SKArcShape)try2).bifur=true;
               if(try2.fixed==true)
               {
                  yes = true;
                  fix=try1.getSubShape(1);
                  System.out.println("bifur fix"+fix);
                  fix1=try2.getSubShape(1);
                  count++;
               }
               if(frameMain.solvingShapes.indexOf(try1) == -1)
                  frameMain.solvingShapes.add(try1);
            }
         }
         repaint();
         if(count>1)
            dialog.showMessageDialog(frameMain.panelShapeArea,"can not display partially solved sketch");
      }
      public void paint(Graphics g)
      {
         super.paint(g);
         for(int i=0; i<Shapes.size(); i++)
            Shapes.get(i).paintConstraints(g);
         pnlShapes.drawNames(g);
         return;
      }
   
      void pnlShapes_mouseMoved(MouseEvent e)
      {
      //Update mouse coordinates on statusbar
      //sbStatus.updatePanelText(e.getX()+" , "+e.getY(),2);
         boolean wasNull = (frameMain.mouseOverItem==null);
         if (frameMain.mouseOverItem==null || !frameMain.mouseOverItem.getClonedItem().doHitTest(e.getX(),e.getY()) )
         {
            for (int i=0; i<frameMain.DrawnItems.size(); i++)
            {
               frameMain.mouseOverItem = frameMain.DrawnItems.get(i);
               if ( frameMain.mouseOverItem.getClonedItem().doHitTest(e.getX(),e.getY()) )
               {
                  frameMain.sbStatus.updatePanelText( frameMain.mouseOverItem.toString(),3);
                  pnlShapes.repaint();
                  return;
               }
            }
         
            frameMain.mouseOverItem=null;
            frameMain.sbStatus.updatePanelText("",3);
            if (!wasNull)  pnlShapes.repaint();
         }
      }
   
      void pnlShapes_mouseDragged(MouseEvent e)
      {
      /* For dragging DrawnItems (dragging lineShape by line)
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
         if (!pnlShapes.DrawSelRect)
         {
            pnlShapes.DrawSelRect = true;
         
            pnlShapes.StartX = e.getX();
            pnlShapes.StartY = e.getY();
         }
         else
         { //Draw selection rect
            pnlShapes.EndX = e.getX();
            pnlShapes.EndY = e.getY();
         
            pnlShapes.repaint();
         }
      //    }
      }
   
      void pnlShapes_mouseReleased(MouseEvent e)
      {
         if (frameMain.mouseOverItem != null && frameMain.mouseOverItem instanceof SKBaseShape)
            ((SKBaseShape)frameMain.mouseOverItem).DragState = -1;
      
         if (e.isPopupTrigger())
         {
            frameMain.popupShape.show(pnlShapes,e.getX(),e.getY());
            return;
         }
      
         if (pnlShapes.DrawSelRect)
         { //Selected shapes in rect and repaint
            if (!e.isControlDown()) frameMain.clearSelectedShapes(true,true);
         
            int sx,sy,ex,ey;
            if (pnlShapes.StartX<pnlShapes.EndX)
            {
               sx = pnlShapes.StartX;
               ex = pnlShapes.EndX;
            }
            else
            {
               sx = pnlShapes.EndX;
               ex = pnlShapes.StartX;
            }
            if (pnlShapes.StartY<pnlShapes.EndY)
            {sy = pnlShapes.StartY;
               ey = pnlShapes.EndY;
            }
            else
            {
               sy = pnlShapes.EndY;
               ey = pnlShapes.StartY;
            }
         
            SKBaseShape sh;
            for (int i=0; i<pnlShapes.getComponentCount(); i++)
            {
               sh = (SKBaseShape)pnlShapes.getComponent(i);
               if (sh.getX()>sx && (sh.getX()+sh.getWidth())<ex &&
                  sh.getY()>sy && (sh.getY()+sh.getHeight()<ey))
               {
                  frameMain.addSelectedShape(sh);
               }
            }
         
            pnlShapes.DrawSelRect=false;
            pnlShapes.repaint();
         }
      }
   
      void pnlShapes_mouseClicked(MouseEvent e)
      {
         if (e.isPopupTrigger())
         {
            return;
         }
      
         if (frameMain.mouseOverItem != null)
         {
            if (frameMain.mouseOverItem instanceof SKBaseShape)
            {
               if (e.isControlDown())
                  frameMain.toggleSelectedShape((SKBaseShape)frameMain.mouseOverItem);
               else
                  frameMain.addOnlySelectedShape((SKBaseShape)frameMain.mouseOverItem);
            }
            else
               frameMain.EditConstraint( (SKBaseConstraint)frameMain.mouseOverItem );
         
            return;
         }
      }
   
      void scrVert_adjustmentValueChanged(AdjustmentEvent e)
      {
         doTranslate(0,lastVertScroll-e.getValue());
         set_vervalue(scrVert.getValue());
      }
   
      void scrHoriz_adjustmentValueChanged(AdjustmentEvent e)
      {
         doTranslate(lastHorizScroll-e.getValue(),0);
         set_horvalue(scrHoriz.getValue());
      }
   
      void cmbScale_itemStateChanged(ItemEvent e)
      {
         if (e.getStateChange()==e.SELECTED)
            doScale((Float.parseFloat(cmbScale.getSelectedItem().toString())) / 100);
      }
   
      void btnPrevious_actionPerformed(ActionEvent e)
      
      
      { 
         if (currBifurcation > 0)
            setBifurcation(currBifurcation-1);
         repaint();
         frameMain.repaint();
         scrHoriz.setValue(horvalue);
         scrVert.setValue(vervalue);
      }
   
      void btnNext_actionPerformed(ActionEvent e)
      {  
         if (currBifurcation < bifurcations.size()-1)
            setBifurcation(currBifurcation+1);
         repaint();
         frameMain.repaint();
         scrHoriz.setValue(horvalue);
         scrVert.setValue(vervalue);
      
      }
      void btnCancel_actionPerformed(ActionEvent e)
      {
         approved = false;
         frameMain.allshapes.copyArrayTo(frameMain.oldShapes);
         frameMain.allConstraints.copyArrayTo(frameMain.oldConstraints);
         frameMain.oldShapeID = frameMain.IDCnt - 1;
         frameMain.oldConstraintID = frameMain.ConstrIDCnt;
         frameMain.oldGroupID = frameMain.GroupIDCnt;
         for(int i=0; i<frameMain.oldConstraints.size(); i++)
         {
            if(frameMain.oldConstraints.get(i) instanceof SKDistanceConstraint)
               frameMain.consArray[frameMain.oldConstraints.get(i).ID] = ((SKDistanceConstraint)frameMain.oldConstraints.get(i)).distance;
            
            else if(frameMain.oldConstraints.get(i) instanceof SKAngleConstraint)
               frameMain.consArray[frameMain.oldConstraints.get(i).ID] = ((SKAngleConstraint)frameMain.oldConstraints.get(i)).angle;
         }
         hide();
      }
      void btnConfirm_actionPerformed(ActionEvent e)
      {
         drawShapesOnMain();
         for (int i=0; i<pnlShapes.getComponentCount();i++)
         //         for (int j=1;j<((SKBaseShape)pnlShapes.getComponent(i)).getNumSubShapes()+1; j++)
         {
            SKBaseShape sh =((SKBaseShape)pnlShapes.getComponent(i));
            int id = ((SKBaseShape)pnlShapes.getComponent(i)).ID;
            System.out.println("btncon "+id);
            if(!(sh instanceof SKPointSubShape))
            {SKBaseShape sh1 = frameMain.allshapes.findByID(id);
               System.out.println("btncon "+sh1);
               addPartialShape(sh1);
               if( sh1 instanceof SKNormalShape)sh1.fixed=true;
               else 
                  for(int a=0; a<sh1.getNumSubShapes()+1; a++)
                     sh1.getSubShape(a).fixed=true;
            }
         }
      
         frameMain.mode=2;
         frameMain.IntArray[0]=2;
         frameMain.solvingShapes.clear();
         frameMain.RefreshShapeArea();
         hide();
      }
   
   /////////////////////////////////////////////////////
   //
   //  Functionality
   //
   /////////////////////////////////////////////////////
   
      public void doScale(float newScale)
      {
         float delta = newScale/lastScale;
         Component cmp;
         for(int i=0; i<pnlShapes.getComponentCount(); i++)
         {
            cmp = pnlShapes.getComponent(i);
            cmp.setLocation( java.lang.Math.round(cmp.getX()*delta), java.lang.Math.round(cmp.getY()*delta) );
         }
      
         lastScale = newScale;
      }
   
      public void doTranslate(int deltaX, int deltaY)
      {
         //Component cmp;
         SKBaseShape cmp;
         for (int i=0; i<pnlShapes.getComponentCount(); i++)
         {
            cmp = (SKBaseShape)pnlShapes.getComponent(i);
            //System.out.println("before "+cmp+" "+cmp.getX()+" "+cmp.getY()+" "+deltaX+" "+deltaY);
            if(cmp instanceof SKNormalShape)cmp.setLocation(cmp.getX()+deltaX,cmp.getY()+deltaY);
            else 
               for(int a=1; a<cmp.getNumSubShapes()+1; a++)
                  cmp.setShapePoint(new Point(cmp.getX()+deltaX,cmp.getY()+deltaY));
            //System.out.println("after "+cmp+" "+cmp.getX()+" "+cmp.getY());
         }
         pnlShapes.repaint();
      
         if (deltaX != 0)  lastHorizScroll = lastHorizScroll - deltaX;
         if (deltaY != 0)  lastVertScroll = lastVertScroll - deltaY;
         repaint();
      }
   
      void set_vervalue(int i)
      {
         if(i!=0)vervalue = i;
      }
   
      void set_horvalue(int i)
      {
         if(i!=0)horvalue = i;
      }
      public void setBifurcation(int index)
      {
         if (currBifurcation == -1)
         { //Init display
            frameMain.clearSelectedShapes(true,true);
            for (int i=0; i<pnlShapes.getComponentCount(); i++)
               frameMain.addSelectedShape( ((SKBaseShape)pnlShapes.getComponent(i)).clonedShape );
               //frameMain.addSelectedShape( ((SKBaseShape)pnlShapes.getComponent(i)));
         
         }
      
         if (index != currBifurcation && index < bifurcations.size())
         {
            scrHoriz.setValue(0);
            scrVert.setValue(0);
            cmbScale.setSelectedIndex(1);
            bifurcations.updateShapes(index,this);
            for(int i=0; i<pnlShapes.getComponentCount(); i++)
               for(int j=1; j<((SKBaseShape)pnlShapes.getComponent(i)).getNumSubShapes()+1; j++)
                  ((SKBaseShape)pnlShapes.getComponent(i)).getSubShape(j).repaint();
            pnlShapes.repaint();
            currBifurcation = index;
            lblStatus.setText(" Bifurcation "+(currBifurcation+1)+" / "+bifurcations.size()+" ");
         }
         SKBifurcation bifur=bifurcations.get(currBifurcation);
         frameMain.RefreshShapeArea();
         for(int i=0; i<frameMain.allshapes.size(); i++)
            frameMain.allshapes.get(i).repaint();
      }
   
      private void drawShapesOnMain()
      
      {
         int maxX=0, maxY=0, tempX, tempY, count = 0,x,y;
         SKBaseShape fix=null,fix1=null,tip = null;
         SKShapeArray fixedShapes = new SKShapeArray(10);
         SKShapeArray allSubShapes = new SKShapeArray(50);
         SKShapeArray solvingShapes = new SKShapeArray(50);
         SKShapeArray nonSolvingShapes = new SKShapeArray(50);
         JOptionPane dialog = null;
      
         for(int i=0; i<frameMain.panelShapeArea.getComponentCount(); i++)
            //for(int j=1; j<((SKBaseShape)frameMain.panelShapeArea.getComponent(i)).getNumSubShapes()+1; j++)
         {
            allSubShapes.add((SKBaseShape)frameMain.panelShapeArea.getComponent(i));
            if(((SKBaseShape)frameMain.panelShapeArea.getComponent(i)).fixed) 
               fixedShapes.add((SKBaseShape)frameMain.panelShapeArea.getComponent(i));
         }
         if(fixedShapes.size()>0)
            frameMain.panelPartial.removeAll();
         for(int i=0; i<fixedShapes.size(); i++)
            addPartialShape(fixedShapes.get(i));
      
         allSubShapes.copyArrayTo(nonSolvingShapes);
         for(int i=0; i<pnlShapes.getComponentCount(); i++)
            for(int j=1; j<((SKBaseShape)pnlShapes.getComponent(i)).getNumSubShapes()+1; j++)
            {
               for(int k=0; k<allSubShapes.size(); k++)
                  if(allSubShapes.get(k).ID == ((SKBaseShape)pnlShapes.getComponent(i)).getSubShape(j).ID)
                  {
                     nonSolvingShapes.removeShape(allSubShapes.get(k));
                     solvingShapes.add(allSubShapes.get(k));
                  }
               for(int k=0; k<fixedShapes.size(); k++)
                  if(fixedShapes.get(k).ID == ((SKBaseShape)pnlShapes.getComponent(i)).getSubShape(j).ID)
                  {
                     fix = ((SKBaseShape)pnlShapes.getComponent(i)).getSubShape(j);
                     count++;
                  }
            }
         if(count<=1)
         {
            //for(int i=0; i<fixedShapes.size(); i++)
            //{
            for(int i=0; i<frameMain.panelShapeArea.getComponentCount(); i++)
               for(int j=1; j<((SKBaseShape)frameMain.panelShapeArea.getComponent(i)).getNumSubShapes()+1; j++)
               {SKBaseShape t = ((SKBaseShape)frameMain.panelShapeArea.getComponent(i)).getSubShape(j);
                  if(maxX<=t.getX())
                  {
                     tip = t;
                     maxX = t.getX();
                  }
               }
            if(count ==0)
            {
               fix = ((SKBaseShape)pnlShapes.getComponent(0)).getSubShape(1);
               for(int i=0; i<pnlShapes.getComponentCount(); i++)
                  for(int j=1; j<((SKBaseShape)pnlShapes.getComponent(i)).getNumSubShapes()+1; j++)
                     if(fix.getX()>((SKBaseShape)pnlShapes.getComponent(i)).getSubShape(j).getX())
                        fix = ((SKBaseShape)pnlShapes.getComponent(i)).getSubShape(j);
            
               for(int i=0; i<frameMain.panelShapeArea.getComponentCount(); i++)
                  for(int j=1; j<((SKBaseShape)frameMain.panelShapeArea.getComponent(i)).getNumSubShapes()+1; j++)
                  {
                     if(fix.ID == ((SKBaseShape)frameMain.panelShapeArea.getComponent(i)).getSubShape(j).ID)
                        fix1 = ((SKBaseShape)frameMain.panelShapeArea.getComponent(i)).getSubShape(j);
                  }
               fix1.setShapePoint(new Point((tip.getShapeX()+5),(tip.getShapeY())));
            }
            else if(count ==1)
            {
               for(int i=0; i<frameMain.panelShapeArea.getComponentCount(); i++)
                  for(int j=0; j<((SKBaseShape)frameMain.panelShapeArea.getComponent(i)).getNumSubShapes(); j++)
                     if(fix.ID == ((SKBaseShape)frameMain.panelShapeArea.getComponent(i)).getSubShape(j).ID)
                        fix1 = ((SKBaseShape)frameMain.panelShapeArea.getComponent(i)).getSubShape(j);
            }
         
            for (int i=0; i<pnlShapes.getComponentCount();i++)
               for(int j=1; j<((SKBaseShape)pnlShapes.getComponent(i)).getNumSubShapes()+1;j++)
               {
                  SKBaseShape sh = ((SKBaseShape)pnlShapes.getComponent(i)).getSubShape(j);
                  if(sh.ID!=fix.ID)
                  {  SKBaseShape sh1,sh1P;
                     int id =((SKBaseShape)pnlShapes.getComponent(i)).ID;
                     SKBaseShape temp = (SKBaseShape)pnlShapes.getComponent(i);
                     if(!(temp instanceof SKNormalShape)) 
                     {sh1 = frameMain.allshapes.findByID(id).getSubShape(j);
                        sh1P = frameMain.allshapes.findByID(id);}
                     else
                     { sh1=temp;
                        sh1P=sh1;}
                     SKBifurcation bifur=bifurcations.get(currBifurcation);
                     x=(fix.getShapeX()-sh.getShapeX());
                     y=(fix.getShapeY()-sh.getShapeY());
                     if(sh1.ID!=fix1.ID && !sh1.fixed)
                        sh1.setShapePoint(new Point((fix1.getShapeX()-x),(fix1.getShapeY()-y)));
                     sh1P.repaint();
                  }
               }
            int p1x=fix1.getShapeX(), p1y=0, p2x=fix1.getShapeX(), p2y=fix1.getShapeY()+300, px=0, py=0;
            boolean outside=false;
            double rad=0;
            while(!outside && (rad<=java.lang.Math.PI*2))
            {
               outside = true;
               for(int i=0; i<nonSolvingShapes.size(); i++)
               {
                  px = nonSolvingShapes.get(i).getShapeX();
                  py = nonSolvingShapes.get(i).getShapeY();
                  outside = outside && (((py-p1y)*(p2x-p1x))-((px-p1x)*(p2y-p1y)) > 0);
               }
               if(!outside)
               {
                  float r = distance(p1x,p1y,p2x,p2y);
                  rad = rad+(java.lang.Math.PI/36);
                  AffineTransform T = new AffineTransform();
                  T.setToRotation(rad,fix1.getShapeX(),fix1.getShapeY());
               
                  float[] src,dst;
               
                  src = new float[2];
                  dst = new float[2];
                  src[0]=p2x;
                  src[1]=p2y;
                  T.transform(src,0,dst,0,1);
                  p2x=(int)dst[0];
                  p2y=(int)dst[1];
                  //System.out.println("second "+p2x+" "+p2y+" "+rad);
                  for(int i=0; i<solvingShapes.size(); i++)
                  {
                    /* for(int k=0; k<pnlShapes.getComponentCount(); k++)
                        for(int j=0; j<((SKBaseShape)pnlShapes.getComponent(k)).getNumSubShapes(); j++)
                           if(solvingShapes.get(i).ID == ((SKBaseShape)pnlShapes.getComponent(k)).getSubShape(j).ID)
                     {
                        px = ((SKBaseShape)pnlShapes.getComponent(k)).getSubShape(j).getShapeX();
                        py = ((SKBaseShape)pnlShapes.getComponent(k)).getSubShape(j).getShapeY();
                     }*/
                     if(!solvingShapes.get(i).fixed)
                     {
                        px = solvingShapes.get(i).getShapeX();
                        py = solvingShapes.get(i).getShapeY();
                        src[0]=px;
                        src[1]=py;
                        T.transform(src,0,dst,0,1);
                        SKBaseShape sh;
                        for(int k=0; k<frameMain.panelShapeArea.getComponentCount(); k++)
                           for(int j=0; j<((SKBaseShape)frameMain.panelShapeArea.getComponent(k)).getNumSubShapes(); j++)
                              if(solvingShapes.get(i).ID == ((SKBaseShape)frameMain.panelShapeArea.getComponent(k)).getSubShape(j).ID) 
                              {
                                 sh=((SKBaseShape)frameMain.panelShapeArea.getComponent(k)).getSubShape(j);
                                 sh.setShapePoint(new Point((int)dst[0],(int)dst[1]));
                                 //System.out.println("set "+sh+" "+px+" "+py);
                              }
                     }
                  }
               }
            }
         }
      }
      private void addPartialShape(SKBaseShape sh)
      {
         SKBaseShape newSh;
         System.out.println(sh);
         switch (sh.ShapeTypeID)
         {
            case 0:  newSh = new SKPointShape(frameMain,sh.ID,sh.getX(),sh.getY());
               break;
            case 3:  newSh = new SKLineShape(frameMain,sh.ID,sh.getX(),sh.getY());
               break;
            case 4:  newSh = new SKCircleShape(frameMain,sh.ID,sh.getX(),sh.getY());
               break;
            case 5:  newSh = new SKArcShape(frameMain,sh.ID,sh.getX(),sh.getY());
               break;
            default: newSh = null;
         }
         if (newSh != null)
         {  //System.out.println("array"+newSh);
            sh.cloneShape(newSh);
            frameMain.panelPartial.add(newSh);
            frameMain.panelPartial.repaint();
         }
      }
   
      public static float distance(int x1, int y1, int x2, int y2)
      
      
      {
         return (float)java.lang.Math.sqrt(java.lang.Math.pow((double)(x1-x2),2)+java.lang.Math.pow((double)(y1-y2),2));
      }
   
   }
