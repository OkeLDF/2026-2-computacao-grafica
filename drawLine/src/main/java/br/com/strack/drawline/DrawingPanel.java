package br.com.strack.drawline;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author okeldf
 */
// JPanel que implementa o algoritmo de desenho
class DrawingPanel extends JPanel {
// mousePressed e mouseReleased definem os pontos da linha
// algoritmo de Bresenham implementado aqui
    public int mouseX;
    public int mouseY;
    public int startX;
    public int startY;
    public boolean drawing = false;
    public ArrayList<Line> lines = new ArrayList<>();
    public boolean usingBresenham = true;
    public int lineSize = 1;
    
    public DrawingPanel(){
        setBackground(Color.WHITE);
        
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
            
            @Override
            public void mouseDragged(MouseEvent e){
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
        });
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                drawing = true;

                startX = e.getX();
                startY = e.getY();

                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e){
                drawing = false;
                
                mouseX = e.getX();
                mouseY = e.getY();

                lines.add(new Line(startX, startY, mouseX, mouseY, usingBresenham, lineSize));

                repaint();
            }
        });

        setLayout(null);
        
        JButton changeBtn = new JButton("Using: " + ((usingBresenham)?"Bresenham":"Naive"));
        changeBtn.setBounds(10, 10, 150, 30);
        changeBtn.addActionListener(e -> {
            usingBresenham = !usingBresenham;
            changeBtn.setText("Using: " + ((usingBresenham)?"Bresenham":"Naive"));
        });
        
        JButton clearBtn = new JButton("Clear lines");
        clearBtn.setBounds(10, 50, 150, 30);
        clearBtn.addActionListener(e -> {
            lines.clear();
        });

        JLabel sizeLabel = new JLabel("Line Size:");
        JLabel confirmLabel = new JLabel("(confirm with ENTER)");
        sizeLabel.setBounds(10, 90, 80, 30);
        confirmLabel.setBounds(10, 115, 150, 30);

        JSpinner sizeSpinner = new JSpinner(
            new SpinnerNumberModel(1, 1, 10, 1)
        );
        sizeSpinner.setBounds(100, 90, 60, 30);

        sizeSpinner.addChangeListener(e -> {
            lineSize = (int) sizeSpinner.getValue();
        });

        add(sizeLabel);
        add(confirmLabel);
        add(sizeSpinner);

        add(sizeSpinner);
        add(changeBtn);
        add(clearBtn);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        lines.forEach(line -> line.draw(g));
        
        if (drawing) {
            Line currentLine = new Line(startX, startY, mouseX, mouseY, usingBresenham, lineSize);
            currentLine.draw(g);
        }
        
        g.setColor(new Color(32, 32, 32));
        g.setFont(new Font("Serif", Font.PLAIN, 20));
        g.drawString("(" + mouseX + ", " + mouseY + ")", 10, this.getHeight() - 20);
    }
}