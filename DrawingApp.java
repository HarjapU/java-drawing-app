import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/*
* Name: Harjap Uppal
* Student ID: 501316128
*/

public class DrawingApp extends JFrame implements ActionListener, MouseListener {

    /*
     * Define/Initialize any variables
     */
    private JPanel canvas;
    private JComboBox<String> shapeSelect, colorSelect;
    private JLabel shapeTitle, sizeTitle, colorTitle, redTitle, greenTitle, blueTitle, colorPreview;
    private JSlider sizeSlider, redSlider, greenSlider, blueSlider;
    private JButton clearButton, exitButton, helpButton;
    private JToggleButton fillToggle, eraserToggle;
    private JRadioButton simpleButton, advancedButton;
    private ButtonGroup colorSettingGroup;
    private String shapeSelected, colorSelected;
    private String[] shapeOptions = {"Rectangle", "Square", "Circle"}, colorOptions = {"Black", "Blue", "Green", "Red", "Magenta", "Orange"}; // used for JComboBoxes
    private int shapeWidth, shapeHeight, sizeMultiplier;
    private boolean filled = false, eraser = false;
    
    /**
     * Constructor for the DrawingApp.
     * Sets up the JFrame, UI components, layout, event listeners, and canvas.
     */
    public DrawingApp() {

        // set JFrame attributes
        super("Drawing App");
        setSize(1500,800);
        setLocationRelativeTo(null); //this sets the JFrame in the center when the program is ran
        setLayout(null);
        getContentPane().setBackground(Color.lightGray);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create a JComboBox (drop down menu) for the shape types and insert the string array of shape type options
        shapeSelect = new JComboBox<>(shapeOptions);
        shapeSelect.setBounds(1230, 50, 100,20);
        add(shapeSelect);

        // Create a JCombobox (drop down menu) for the color options and insert the string array of color options
        colorSelect = new JComboBox<>(colorOptions);
        colorSelect.setBounds(1230, 200, 100,20);
        add(colorSelect);

        // create JLabel for the title above shape JComboBox
        shapeTitle = new JLabel("Shape:");
        shapeTitle.setBounds(1230, 30, 100,20);
        add(shapeTitle);

        // create JLabel for the title above the size JSlider
        sizeTitle = new JLabel("Size:");
        sizeTitle.setBounds(1230, 85, 100 ,20);
        add(sizeTitle);

        // create JLabel for the title above color JComboBox (when in simple mode)
        colorTitle = new JLabel("Color:");
        colorTitle.setBounds(1230, 170, 100, 20);
        add(colorTitle);
        
        // create Jlabel for the title above the red JSlider (when in advanced mode)
        redTitle = new JLabel("Red:");
        redTitle.setBounds(1230, 200, 100, 20);
        add(redTitle);

        // create JLabel for the title above the green JSlider (when in advanced mode)
        greenTitle = new JLabel("Green:");
        greenTitle.setBounds(1230, 280, 100,20);
        add(greenTitle);

        // create JLabel for the title above the blue JSlider (when in advanced mode)
        blueTitle = new JLabel("Blue:");
        blueTitle.setBounds(1230, 360, 100,20);
        add(blueTitle);

        // create a JLabel to show the preview of the choosen color when adjusting the color sliders (when in advanced mode)
        colorPreview = new JLabel();
        colorPreview.setFont(blueTitle.getFont().deriveFont((float) 30)); // get the font used everywhere else and only change the font size
        colorPreview.setBounds(1220, 450, 250, 30);
        colorPreview.setOpaque(true); // this allows the background color to be changed
        colorPreview.setBackground(Color.black); // set default background color to be black when program is ran
        add(colorPreview);

        // create JButton to clear the canvas
        clearButton = new JButton("Clear");
        clearButton.setBounds(1230, 700, 70 ,30);
        clearButton.addActionListener(this); // add to the JFrame actionlistener
        add(clearButton);

        // create JButton to show some instructions for the app
        helpButton = new JButton("Help");
        helpButton.setBounds(1305, 700, 70 ,30);
        helpButton.addActionListener(this); // add to the JFrame actionlistener
        add(helpButton);

        // create JButton to formally exit the program
        exitButton = new JButton("Exit");
        exitButton.setBounds(1380, 700, 70 ,30);
        exitButton.addActionListener(this); // add to the JFrame actionlistener
        add(exitButton);
        
        // Add a JRadioButton (button with circle on the side) to toggle the simple mode for colors
        simpleButton = new JRadioButton("Simple");
        simpleButton.setBounds(1270, 170, 80, 20);
        simpleButton.setBackground(Color.lightGray); // blends in with background
        simpleButton.addActionListener(this); 
        add(simpleButton);

        // Add a JRadioButton (button with circle on the side) to toggle the advanced mode for colors
        advancedButton = new JRadioButton("Advanced");
        advancedButton.setBounds(1345, 170, 100, 20);
        advancedButton.setBackground(Color.lightGray); // blends in with background
        advancedButton.addActionListener(this); // add to the JFrame actionlistener
        add(advancedButton);

        // Create a ButtonGroup to group the simple and advanced buttons together. By grouping them together, now only one of them can be active at a time and turning one button
        // on will turn the other button off
        colorSettingGroup = new ButtonGroup();
        colorSettingGroup.add(simpleButton);
        colorSettingGroup.add(advancedButton);

        // Create a JToggleButton to toggle the fill mode on or off
        fillToggle = new JToggleButton("Fill Mode");
        fillToggle.setBounds(1357, 32, 105, 25);
        fillToggle.addActionListener(this); // add to the JFrame actionlistener
        add(fillToggle);

        // create a JToggleButton to toggle the eraser mode on or off
        eraserToggle = new JToggleButton("Erase Mode");
        eraserToggle.setBounds(1357, 60, 105, 25);
        eraserToggle.addActionListener(this); // add to the JFrame actionlistener
        add(eraserToggle);

        // create a JSlider to choose the size of the shapes by dragging a slider
        sizeSlider = new JSlider(0,100); // sets the slider range from 0 to 100
        sizeSlider.setPaintTicks(true); // to see the ticks on the slider
        sizeSlider.setPaintLabels(true); // to see the numbers on the slider
        sizeSlider.setMajorTickSpacing(25); // set major and minor tick spacing
        sizeSlider.setMinorTickSpacing(5);
        sizeSlider.setBackground(Color.lightGray);
        sizeSlider.setForeground(Color.black);
        sizeSlider.setBounds(1220, 110, 250, 45);
        add(sizeSlider);

        // create a JSlider to choose the amount of red in the color for each shape
        redSlider = new JSlider(0,255); // sets the slider range from 0 to 255 (range of RGB colors)
        redSlider.setPaintTicks(true); // to see the ticks on the slider
        redSlider.setPaintLabels(true); // to see the numbers on the slider
        redSlider.setMajorTickSpacing(85); // set major and minor tick spacing
        redSlider.setMinorTickSpacing(17);
        redSlider.setValue(0); // set starting value to 0 when the program is ran
        redSlider.setBackground(Color.lightGray);
        redSlider.setForeground(Color.black);
        redSlider.setBounds(1220, 220, 250, 45);
        redSlider.addMouseListener(this); // add to the JFrame mouselistener
        add(redSlider);

        // create a JSlider to choose the amount of green in the color for each shape
        greenSlider = new JSlider(0,255); // sets the slider range from 0 to 255 (range of RGB colors)
        greenSlider.setPaintTicks(true); // to see the ticks on the slider
        greenSlider.setPaintLabels(true); // to see the numbers on the slider
        greenSlider.setMajorTickSpacing(85); // set major and minor tick spacing
        greenSlider.setMinorTickSpacing(17);
        greenSlider.setValue(0); // set starting value to 0 when the program is ran
        greenSlider.setBackground(Color.lightGray);
        greenSlider.setForeground(Color.black);
        greenSlider.setBounds(1220, 300, 250, 45);
        greenSlider.addMouseListener(this); // add to the JFrame mouselistener
        add(greenSlider);

        // create a JSlider to choose the amount of blue in the color for each shape
        blueSlider = new JSlider(0,255); // sets the slider range from 0 to 255 (range of RGB colors)
        blueSlider.setPaintTicks(true); // to see the ticks on the slider
        blueSlider.setPaintLabels(true); // to see the numbers on the slider
        blueSlider.setMajorTickSpacing(85); // set major and minor tick spacing
        blueSlider.setMinorTickSpacing(17);
        blueSlider.setValue(0); // set starting value to 0 when the program is ran
        blueSlider.setBackground(Color.lightGray);
        blueSlider.setForeground(Color.black);
        blueSlider.setBounds(1220, 380, 250, 45);
        blueSlider.addMouseListener(this); // add to the JFrame mouselistener
        add(blueSlider);

        // create the JPanel where the shapes will be drawn
        canvas = new JPanel();

        // set canvas attributes
        canvas.setSize(1200,800);
        canvas.setBackground(Color.white);
        canvas.setLayout(null);

        // create a new mouselistener and add it to the canvas to draw the shapes on mouse presses
        canvas.addMouseListener(new MouseListener() {

            /*
             * This event method will run on every mouse press on the canvas JPanel
             * This method will draw based on shape type, color, size and fill/eraser toggles set by the user
             */
            @Override
            public void mousePressed(MouseEvent e) {
                // get the graphics used on the canvas JPanel and do all operations using that graphics
                Graphics g = canvas.getGraphics();

                // if the eraser mode is active (eraser button is toggled), then force the color to be white
                if (eraser) {
                    g.setColor(Color.white);
                }
                // if in simple color mode (simple radio button is active), then get the color selected in the color options JComboBox and set it to the graphics
                else if (simpleButton.isSelected()) {
                    colorSelected = (String) colorSelect.getSelectedItem();

                    if (colorSelected == "Black") {
                        g.setColor(Color.black);
                    }
                    else if (colorSelected == "Blue") {
                        g.setColor(Color.blue);
                    }
                    else if (colorSelected == "Green") {
                        g.setColor(Color.green);
                    }
                    else if (colorSelected == "Red") {
                        g.setColor(Color.red);
                    }
                    else if (colorSelected == "Magenta") {
                        g.setColor(Color.magenta);
                    }
                    else if (colorSelected == "Orange") {
                        g.setColor(Color.orange);
                    }
                }
                // if in advanced color mode (advanced radio button is active), then create a new color based on RGB values from the RGB JSliders and set it as the graphics color
                else if (advancedButton.isSelected()) {
                    g.setColor(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
                }

                shapeSelected = (String) shapeSelect.getSelectedItem(); // get the shape type from the shape type JComboBox
                sizeMultiplier = (sizeSlider.getValue()); // get the size multiplier for the size JSlider

                // if shape is rectangle, then create rectangle based on fill/eraser toggle and size multipler
                if (shapeSelected == "Rectangle") {
                    shapeWidth = 10 * sizeMultiplier;
                    shapeHeight = 5 * sizeMultiplier;

                    if (filled || eraser) {
                        // set position so middle of rectangle shows up on the mouse
                        g.fillRect(e.getX()-(shapeWidth/2), e.getY()-(shapeHeight/2), shapeWidth, shapeHeight);
                    }
                    else {
                        // set position so middle of rectangle shows up on the mouse
                        g.drawRect(e.getX()-(shapeWidth/2), e.getY()-(shapeHeight/2), shapeWidth, shapeHeight);
                    }
                }
                // if shape is square, then create square based on fill/eraser toggle and size multipler
                else if (shapeSelected == "Square") {
                    shapeWidth = 4 * sizeMultiplier;
                    shapeHeight = 4 * sizeMultiplier;

                    if (filled || eraser) {
                        // set position so middle of square shows up on the mouse
                        g.fillRect(e.getX()-(shapeWidth/2), e.getY()-(shapeHeight/2), shapeWidth, shapeHeight);
                    }
                    else {
                        // set position so middle of square shows up on the mouse
                        g.drawRect(e.getX()-(shapeWidth/2), e.getY()-(shapeHeight/2), shapeWidth, shapeHeight);
                    }

                }
                // if shape is circle, then create circle based on fill/eraser toggle and size multipler
                else if (shapeSelected == "Circle") {
                    shapeWidth = 4 * sizeMultiplier;
                    shapeHeight = 4 * sizeMultiplier;

                    if (filled || eraser) {
                        // set position so middle of circle shows up on the mouse
                        g.fillOval(e.getX()-(shapeWidth/2), e.getY()-(shapeHeight/2), shapeWidth, shapeHeight);
                    }
                    else {
                        // set position so middle of circle shows up on the mouse
                        g.drawOval(e.getX()-(shapeWidth/2), e.getY()-(shapeHeight/2), shapeWidth, shapeHeight);
                    }

                }

            }

            /*
             * Unused methods
             */
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            
        });
        add(canvas);

        simpleButton.doClick(); // activate simple mode on program start
        setVisible(true);
        
    }

    public static void main(String[] args) {
        new DrawingApp();
    }

    /**
     * Handles all action events from the Swing components (e.g., shape/color selection,
     * toggles, buttons) and updates the application/canvas accordingly.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // if the fill toggle is clicked and check toggle status of button and set boolean
        if (e.getSource() == fillToggle) {
            if (fillToggle.isSelected()) {
                filled = true;
            }
            else {
                filled = false;
            }
        }
        // if the eraser toggle is clicked and check toggle status of button and set boolean.
        // Also enable/disable features depending on toggle status
        else if (e.getSource() == eraserToggle) {
            if (eraserToggle.isSelected()) {
                eraser = true;
                
                simpleButton.setEnabled(false);
                advancedButton.setEnabled(false);
                colorSelect.setEnabled(false);
                redSlider.setEnabled(false);
                greenSlider.setEnabled(false);
                blueSlider.setEnabled(false);
                fillToggle.setEnabled(false);
                
            }
            else {
                eraser = false;

                simpleButton.setEnabled(true);
                advancedButton.setEnabled(true);
                colorSelect.setEnabled(true);
                redSlider.setEnabled(true);
                greenSlider.setEnabled(true);
                blueSlider.setEnabled(true);
                fillToggle.setEnabled(true);

            }
        }
        // if simple mode is active, then show and hide appropriate JComponents
        else if (e.getSource() == simpleButton) {
            redSlider.setVisible(false);
            greenSlider.setVisible(false);
            blueSlider.setVisible(false);
            redTitle.setVisible(false);
            greenTitle.setVisible(false);
            blueTitle.setVisible(false);
            colorPreview.setVisible(false);

            colorSelect.setVisible(true);

        }
        // if advanced mode is active, then show and hide appropriate JComponents
        else if (e.getSource() == advancedButton) {
            colorSelect.setVisible(false);

            redSlider.setVisible(true);
            greenSlider.setVisible(true);
            blueSlider.setVisible(true);
            redTitle.setVisible(true);
            greenTitle.setVisible(true);
            blueTitle.setVisible(true);
            colorPreview.setVisible(true);
        }
        // if clear button is clicked, then clear the canvas
        else if (e.getSource() == clearButton) {
            canvas.repaint();
        }
        // if help button is clicked, then show a seperate window with instructions on using the app
        else if (e.getSource() == helpButton) {
            JOptionPane.showMessageDialog(this, "- To start drawing, simply click on the left side of the screen\n- The right side has options to change the shape type," + 
            " size, color, and fill mode\n- You can also toggle the eraser mode to fix any mistakes, but must be re-toggled before trying to draw again\n- The clear" + 
            " button will clear the canvas\n- The exit button will exit the App\n\nEnjoy!", "How to Use", JOptionPane.INFORMATION_MESSAGE);
        }
        // if exit button is clicked, then exit the app
        else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
    
    /*
     * This method will be ran every time the mouse is released on a JComponent with a mouse listener attacted to the JFrame
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // whenever the mouse releases the sliders, update the color preview on the JFrame
        colorPreview.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
    }

    /*
     * Unused methods
     */
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
