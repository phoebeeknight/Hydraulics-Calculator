
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import javax.swing.ToolTipManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class HydraulicsCalc extends javax.swing.JFrame {

    /**
     * Creates new form HydraulicsCalc
     * @param err
     * @param errPlace
     */

    public void ErrorText(String err, String errPlace)
    {
        if(errPlace.isEmpty())
            jTextArea1.append("Error : " + err + "\n");
        else
            jTextArea1.append("Error : " + err + " ( " + errPlace + " ) " + "\n");
        
        jTextArea1.setForeground(Color.red);
    }
    
    public boolean ErrorCheckCalc()
    {
        boolean out = false;
        
        //Shape Channel
        if(cbShape.getSelectedIndex() == 0)
        {
            ErrorText("Please select the Shape Channel","");
            out = true;
        }
        else
        {
            if(cbShape.getSelectedIndex() == 1) //Rectangular 
            {
                if(btxt.getText().isEmpty() || ytxt.getText().isEmpty())
                {
                    ErrorText("B and y field cannot be empty","");
                    out = true;
                }
            }
            else if(cbShape.getSelectedIndex() == 2) //Trapezoidal
            {
                if(btxt.getText().isEmpty() || ztxt.getText().isEmpty() || ytxt.getText().isEmpty())
                {
                    ErrorText("B, z and y field cannot be empty","");
                    out = true;
                }
            }
            else //Triangular
            {
                if(ztxt.getText().isEmpty() || ytxt.getText().isEmpty())
                {
                    ErrorText("z and y field cannot be empty","");
                    out = true;
                }
            }
        }
        
        //Formular Equation
        if(cbEquation.getSelectedIndex() == 0)
        {
            ErrorText("Please select the Formular Equation","");
            out = true;
        }
        else
        {
            if(cbEquation.getSelectedIndex() == 1) //Chezy's  
            {
                if(ctxt.getText().isEmpty() || sotxt.getText().isEmpty())
                {
                    ErrorText("C and So field cannot be empty","");
                    out = true;
                }
            }
            else //Manning's
            {
                if(ntxt.getText().isEmpty() || sotxt.getText().isEmpty())
                {
                    ErrorText("n and So field cannot be empty","");
                    out = true;
                }
            }
        }
        return out;
    }
    
    public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
}
    
    double A = 0.0; double P = 0.0; double T = 0.0; double R = 0.0; double Dm = 0.0; 
    
    public void CalcRect()
    {
        double B = Double.parseDouble(btxt.getText());
        double y = Double.parseDouble(ytxt.getText());
        
        double Ar = 0.0; double Pr = 0.0; double Rr = 0.0; double Dmr = 0.0;
        
        Ar = round(B*y,2);
        Pr = round(B + (2*y),2);
        Rr = round(Ar/Pr,2);
        Dmr = round(y,2);                
        
        jTextArea1.append("\n----Rectangular Channel----");
        jTextArea1.append("\nA = " + Ar + " m² or ft²");
        jTextArea1.append("\nP = " + Pr + " m or ft");
        jTextArea1.append("\nR = " + Rr + " m or ft");
        jTextArea1.append("\nDm = " + Dmr + " m or ft");
        jTextArea1.append("\n---------------------------");
        
        A = Ar; P = Pr; R = Rr; Dm = Dmr;
    }
    
    public void CalcTrape()
    {
        double B = Double.parseDouble(btxt.getText());
        double z = Double.parseDouble(ztxt.getText());
        double y = Double.parseDouble(ytxt.getText());
        
        double Ar = 0.0; double Pr = 0.0; double Tr = 0.0;
        double Rr = 0.0; double Dmr = 0.0;
        
        Ar = round(y*(B+(z*y)),2);
        Pr = round(B + (2*y)*(Math.sqrt(1+z)),2);
        Tr = round(B+(2*z*y),2);
        Rr = round(Ar/Pr,2);
        Dmr = round(Ar/Tr,2);
        
        jTextArea1.append("\n----Trapezoidal Channel----");
        jTextArea1.append("\nA = " + Ar + " m² or ft²");
        jTextArea1.append("\nP = " + Pr + " m or ft");
        jTextArea1.append("\nP = " + Tr + " m or ft");
        jTextArea1.append("\nR = " + Rr + " m or ft");
        jTextArea1.append("\nDm = " + Dmr + " m or ft");
        jTextArea1.append("\n---------------------------");
        
        A = Ar; P = Pr; T = Tr; R = Rr; Dm = Dmr;
    }
    
    public void CalcTriag()
    {
        double z = Double.parseDouble(ztxt.getText());
        double y = Double.parseDouble(ytxt.getText());
        
        double Ar = 0.0; double Pr = 0.0; double Tr = 0.0; 
        double Rr = 0.0; double Dmr = 0.0;
        
        Ar = round(z*Math.pow(y, 2),2);
        Pr = round(2*y*Math.sqrt(1+Math.pow(z, 2)),2);
        Tr = round(2*z*y,2);
        Rr = round(Ar/Pr,2);
        Dmr = round(y/2,2);
        
        jTextArea1.append("\n----Triangular Channel----");
        jTextArea1.append("\nA = " + Ar + " m² or ft²");
        jTextArea1.append("\nP = " + Pr + " m or ft");
        jTextArea1.append("\nP = " + Tr + " m or ft");
        jTextArea1.append("\nR = " + Rr + " m or ft");
        jTextArea1.append("\nDm = " + Dmr + " m or ft");
        jTextArea1.append("\n---------------------------");
        
        A = Ar; P = Pr; T = Tr; R = Rr; Dm = Dmr;
    }
    
    public void CalcChezy()
    {
        double C = Double.parseDouble(ctxt.getText());
        double So = Double.parseDouble(sotxt.getText());
        
        double V = 0.0; double Q = 0.0;
        
        V = round(C * Math.sqrt(R*So),4);
        Q = round(C*A * Math.sqrt(R*So),4);
        
        jTextArea1.append("\n-----Chezy's Equation------");
        jTextArea1.append("\nV = " + V + " m/s or ft/s");
        jTextArea1.append("\nQ = " + Q);
        jTextArea1.append("\n---------------------------");
    }
    
    public void CalcManning()
    {
        double n = Double.parseDouble(ntxt.getText());
        double So = Double.parseDouble(sotxt.getText());
        
        double V = 0.0; double Qs = 0.0; double Qe = 0.0;
        
        V = round((1/n)*Math.pow(R, 2/3)*Math.pow(So, 1/2),4);
        Qs = round((1/n)*A*Math.pow(R, 2/3)*Math.pow(So, 1/2),4);
        Qe = round((1.486/n)*A*Math.pow(R, 2/3)*Math.pow(So, 1/2),4);
        
        jTextArea1.append("\n-----Manning's Equation------");
        jTextArea1.append("\nV = " + V + " m/s or ft/s");
        jTextArea1.append("\nQ = " + Qs + " (SI Unit)");
        jTextArea1.append("\nQ = " + Qe + " (English Unit)");
        jTextArea1.append("\n---------------------------");
    }
    
    public String ToolTipShape()
    {
        String out = "";
        URL urlRect = getClass().getResource("info.png");
        URL urlTra = getClass().getResource("info.png");
        URL urlTri = getClass().getResource("info.png");
        
        out = "------Rectangular Channel----------";
        out += "<br>A = 𝐵𝑦";// (m² or ft²)";
        out += "<br>P = 𝐵+2𝑦";// (m or ft)";
        out += "<br>R = A/P";// (m or ft)";
        out += "<br>Dm = y";// (m or ft)";
        //out += "<br><img src='" + urlRect + "'>";
        //out += "<br>----------------------------------------------";
        
        out += "<br>------Trapezoidal Channel------------";
        out += "<br>A = (𝐵+𝑧𝑦)𝑦";// (m² or ft²)";
        out += "<br>P = 𝐵+2𝑦√(1+𝑧)";// (m or ft)";
        out += "<br>T = 𝐵+2𝑧𝑦";// (m or ft)";
        out += "<br>R = A/P";// (m or ft)";
        out += "<br>Dm = (𝐵+𝑧𝑦)𝑦/(𝐵+2𝑧𝑦)";// (m or ft)";
        //out += "<br><img src='" + urlTra + "'>";
        //out += "<br>----------------------------------------------";
        
        out += "<br>------Triangular Channel------------";
        out += "<br>A = 𝑧𝑦²";// (m² or ft²)";
        out += "<br>P = 2𝑦√(1+𝑧)²";// (m or ft)";
        out += "<br>T = 2𝑧𝑦";// (m or ft)";
        out += "<br>R = A/P";// (m or ft)";
        out += "<br>Dm = (1⁄2)𝑦";// (m or ft)";
        //out += "<br><img src='" + urlTri + "'>";
        //out += "<br>----------------------------------------------";
        
        return out;
    }
    
    public String ToolTipEquation()
    {
        String out = "";
        URL urlChezy = getClass().getResource("info.png");
        URL urlMann = getClass().getResource("info.png");
        
        out = "------Chezy's Equation--------------------";
        out += "<br>V = 𝐶√𝑅𝑆";// (m/s or ft/s)";
        out += "<br>Q = 𝐶𝐴√𝑅𝑆";
        //out += "<br><img src='" + urlChezy + "'>";
        //out += "<br>----------------------------------------------";
        
        out += "<br>------Manning's Equation-----------------";
        out += "<br>V = 1/𝑛 𝑅<sup>2/3</sup>𝑆<sup>1/2</sup>";// (m/s or ft/s)";
        out += "<br>Q = 1/𝑛 A𝑅<sup>2/3</sup>𝑆<sup>1/2</sup> (SI Unit)";
        out += "<br>Q = 1.486/𝑛 A𝑅<sup>2/3</sup>𝑆<sup>1/2</sup> (English Unit)";
        //out += "<br><img src='" + urlMann + "'>";
        //out += "<br>----------------------------------------------";
        
        return out;
    }
    
    public HydraulicsCalc() {
        initComponents();
        
        // Show tool tips immediately
        ToolTipManager.sharedInstance().setInitialDelay(0);
        
        // Get current delay
        int dismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
        // Keep the tool tip showing
        dismissDelay = Integer.MAX_VALUE;
        ToolTipManager.sharedInstance().setDismissDelay(dismissDelay);
    
        ttShape.setToolTipText("<html><p width=\"200\">" + ToolTipShape() + "</p></html>");
        ttEquation.setToolTipText("<html><p width=\"200\">" + ToolTipEquation() + "</p></html>");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbShape = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ztxt = new javax.swing.JTextField();
        ytxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbEquation = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ctxt = new javax.swing.JTextField();
        ntxt = new javax.swing.JTextField();
        sotxt = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        calcBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ttEquation = new javax.swing.JLabel();
        ttShape = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hydraulics Calculator");

        cbShape.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Shape Channel", "Rectangular Channel", "Trapezoidal Channel", "Triangular Channel" }));
        cbShape.setNextFocusableComponent(btxt);
        cbShape.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbShapeItemStateChanged(evt);
            }
        });

        jLabel1.setText("HYDRAULICS CALCULATOR");

        jLabel2.setText("B");

        btxt.setNextFocusableComponent(ztxt);
        btxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btxtKeyPressed(evt);
            }
        });

        jLabel3.setText("y");

        ztxt.setNextFocusableComponent(ytxt);
        ztxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ztxtKeyPressed(evt);
            }
        });

        ytxt.setNextFocusableComponent(cbEquation);
        ytxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ytxtKeyPressed(evt);
            }
        });

        jLabel4.setText("z");

        cbEquation.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Formular Equation", "Chezy's Equation", "Manning's Equation" }));
        cbEquation.setNextFocusableComponent(ctxt);
        cbEquation.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEquationItemStateChanged(evt);
            }
        });

        jLabel5.setText("C");

        jLabel6.setText("n");

        jLabel7.setText("So");

        ctxt.setNextFocusableComponent(ntxt);
        ctxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ctxtKeyPressed(evt);
            }
        });

        ntxt.setNextFocusableComponent(sotxt);
        ntxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ntxtKeyPressed(evt);
            }
        });

        sotxt.setNextFocusableComponent(calcBtn);
        sotxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sotxtKeyPressed(evt);
            }
        });

        calcBtn.setText("Calculate");
        calcBtn.setNextFocusableComponent(resetBtn);
        calcBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcBtnActionPerformed(evt);
            }
        });

        resetBtn.setText("Reset");
        resetBtn.setNextFocusableComponent(cbShape);
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        jLabel8.setText("Results :");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel9.setText("(m or ft)");

        jLabel11.setText("(m or ft)");

        jLabel12.setText("(𝑚½/𝑠)");

        jLabel14.setText("(m/m or ft/ft)");

        jLabel10.setText("By: Nataline, Syahida, Izzatie, Aqilah");

        ttEquation.setText("🛈");

        ttShape.setText("🛈");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(159, 159, 159))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(cbShape, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ttShape))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btxt, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ztxt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ytxt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cbEquation, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ttEquation))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ctxt, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ntxt, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel14)))
                                .addGap(29, 29, 29))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(calcBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resetBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(23, 23, 23))
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbShape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ttShape))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(btxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ztxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ytxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11))
                            .addComponent(jLabel3)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbEquation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ttEquation))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(ctxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(ntxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(sotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetBtn)
                    .addComponent(calcBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbShapeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbShapeItemStateChanged
        // TODO add your handling code here:
        int selectedShape = cbShape.getSelectedIndex();
        
        if(selectedShape == 0)
        {
            //ErrorText("Please select the Shape Channel.", "");
            btxt.setEnabled(true);
            ztxt.setEnabled(true);
            ytxt.setEnabled(true);
        }
        else if(selectedShape == 1) //Rectangular 
        {
            btxt.setEnabled(true);
            ztxt.setEnabled(false);
            ytxt.setEnabled(true);
        }
        else if(selectedShape == 2) //Trapezoidal
        {
            btxt.setEnabled(true);
            ztxt.setEnabled(true);
            ytxt.setEnabled(true);
        }
        else if(selectedShape == 3) //Triangular
        {
            btxt.setEnabled(false);
            ztxt.setEnabled(true);
            ytxt.setEnabled(true);
        }
        else
            ErrorText("Shape Channel", "Combo Box");              
    }//GEN-LAST:event_cbShapeItemStateChanged

    private void cbEquationItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEquationItemStateChanged
        // TODO add your handling code here:
        int selectedEquation = cbEquation.getSelectedIndex();
        
        if(selectedEquation == 0)
        {
            //ErrorText("Please select the Formular Equation.", "");
            ctxt.setEnabled(true);
            ntxt.setEnabled(true);
            sotxt.setEnabled(true);
        }
        else if(selectedEquation == 1) //Chezy's 
        {
            ctxt.setEnabled(true);
            ntxt.setEnabled(false);
            sotxt.setEnabled(true);
        }
        else if(selectedEquation == 2) //Manning's
        {
            ctxt.setEnabled(false);
            ntxt.setEnabled(true);
            sotxt.setEnabled(true);
        }
        else
            ErrorText("Formular Equation", "Combo Box");
    }//GEN-LAST:event_cbEquationItemStateChanged

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        // TODO add your handling code here:
        btxt.setText("");
        ztxt.setText("");
        ytxt.setText("");
        
        ctxt.setText("");
        ntxt.setText("");
        sotxt.setText("");
        
        btxt.setEnabled(true);
        ztxt.setEnabled(true);
        ytxt.setEnabled(true);
        
        ctxt.setEnabled(true);
        ntxt.setEnabled(true);
        sotxt.setEnabled(true);
        
        cbShape.setSelectedIndex(0);
        cbEquation.setSelectedIndex(0);
        
        jTextArea1.setText("");
    }//GEN-LAST:event_resetBtnActionPerformed

    private void calcBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcBtnActionPerformed
        // TODO add your handling code here:      
       int sShape = cbShape.getSelectedIndex();
       int sEquation = cbEquation.getSelectedIndex();
       
       if(!ErrorCheckCalc())
       {
            if(sShape == 1) //Rectangular 
            {
                A = 0.0; P = 0.0; T = 0.0; R = 0.0; Dm = 0.0;

                CalcRect();

                if(sEquation == 1) //Chezy's
                    CalcChezy();
                else
                    CalcManning(); //Manning's
            }
            else if(sShape == 2) //Trapezoidal
            {
                A = 0.0; P = 0.0; T = 0.0; R = 0.0; Dm = 0.0;

                CalcTrape();

                if(sEquation == 1) //Chezy's
                    CalcChezy();
                else
                    CalcManning(); //Manning's
            }
            else //Triangular
            {
                A = 0.0; P = 0.0; T = 0.0; R = 0.0; Dm = 0.0;

                CalcTriag();

                if(sEquation == 1) //Chezy's
                    CalcChezy();
                else
                    CalcManning(); //Manning's
            } 
       }
    }//GEN-LAST:event_calcBtnActionPerformed

    private void btxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btxtKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' ||
            evt.getKeyChar() == evt.VK_BACK_SPACE || evt.getKeyChar() == evt.VK_DELETE || evt.getKeyChar() == '.') {
            btxt.setEditable(true);
        } else {
            btxt.setEditable(false);
        }
    }//GEN-LAST:event_btxtKeyPressed

    private void ztxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ztxtKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' ||
            evt.getKeyChar() == evt.VK_BACK_SPACE || evt.getKeyChar() == evt.VK_DELETE || evt.getKeyChar() == '.') {
            ztxt.setEditable(true);
        } else {
            ztxt.setEditable(false);
        }
    }//GEN-LAST:event_ztxtKeyPressed

    private void ytxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ytxtKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' ||
            evt.getKeyChar() == evt.VK_BACK_SPACE || evt.getKeyChar() == evt.VK_DELETE || evt.getKeyChar() == '.') {
            ytxt.setEditable(true);
        } else {
            ytxt.setEditable(false);
        }
    }//GEN-LAST:event_ytxtKeyPressed

    private void ctxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ctxtKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' ||
            evt.getKeyChar() == evt.VK_BACK_SPACE || evt.getKeyChar() == evt.VK_DELETE || evt.getKeyChar() == '.') {
            ctxt.setEditable(true);
        } else {
            ctxt.setEditable(false);
        }
    }//GEN-LAST:event_ctxtKeyPressed

    private void ntxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ntxtKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' ||
            evt.getKeyChar() == evt.VK_BACK_SPACE || evt.getKeyChar() == evt.VK_DELETE || evt.getKeyChar() == '.') {
            ntxt.setEditable(true);
        } else {
            ntxt.setEditable(false);
        }
    }//GEN-LAST:event_ntxtKeyPressed

    private void sotxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sotxtKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' ||
            evt.getKeyChar() == evt.VK_BACK_SPACE || evt.getKeyChar() == evt.VK_DELETE || evt.getKeyChar() == '.') {
            sotxt.setEditable(true);
        } else {
            sotxt.setEditable(false);
        }
    }//GEN-LAST:event_sotxtKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {       
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HydraulicsCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HydraulicsCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HydraulicsCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HydraulicsCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HydraulicsCalc().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField btxt;
    private javax.swing.JButton calcBtn;
    private javax.swing.JComboBox cbEquation;
    private javax.swing.JComboBox cbShape;
    private javax.swing.JTextField ctxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField ntxt;
    private javax.swing.JButton resetBtn;
    private javax.swing.JTextField sotxt;
    private javax.swing.JLabel ttEquation;
    private javax.swing.JLabel ttShape;
    private javax.swing.JTextField ytxt;
    private javax.swing.JTextField ztxt;
    // End of variables declaration//GEN-END:variables
}
//This code is fully done by Nataline et.al, 2021 UKM