package View;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javafx.embed.swing.SwingFXUtils;
import java.io.*;

/**
 * Allows for a file to be exported out of the system
 */
public class ExportMenuItem extends MenuItem {

    // member variables
    String initalDirectory;

    /**
     * Class constructor.
     * @param label The button label.
     */
    public ExportMenuItem(String label){
        super(label);

        // todo... Gather proper initial directory
        //this.initalDirectory = ??;
    }

    /**
     * Opens a dialog to allow for the given file to be exported.
     * @param initialFilename The name of the file being exported.
     * @return True if the file was exported, false if not.
     */
    public File getFile(Window window, String initialFilename, FileChooser.ExtensionFilter[] extensionFilters){

        // file chooser to save the given file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(initialFilename);
        fileChooser.getExtensionFilters().addAll(extensionFilters);

        // showing the saving dialog
        return fileChooser.showSaveDialog(window);
    }

    /**
     * Saves the given data to the given file.
     * @param content The text to be saved to the file.
     * @param file The file the text will be saved to.
     */
    public void saveToFile(byte[] content, File file){
        try {
            OutputStream out = new FileOutputStream(file);
            out.write(content);
            out.close();
        }
        catch (Exception e) {
            this.displayErrorWindow(e.getMessage());
        }
    }

    /**
     * Writes the given image into the given file.
     * @param image The image to be written into the file.
     * @param imageType The type of the image.
     * @param file The file to write the image into.
     */
    public void saveImageToFile(WritableImage image, String imageType, File file){
        System.out.println("EXPORTING " + imageType);

        BufferedImage bf = null;

        if(imageType.equals("jpeg")) {
            bf = new BufferedImage((int)image.getWidth(), (int)image.getHeight(), BufferedImage.TYPE_INT_RGB);
        }
        try{
            System.out.println(ImageIO.write(SwingFXUtils.fromFXImage(image, bf), imageType, file));
        }
        catch(Exception e){
            this.displayErrorWindow(e.getMessage());
        }
    }

    /**
     * Displays an error window to the screen in the case that the file cannot be saved
     * @param errorMessage
     */
    public void displayErrorWindow(String errorMessage){
        // validation error occured, so need to display error message
        ErrorAlert errorAlert = new ErrorAlert(errorMessage);
        errorAlert.showWindow(this.getParentPopup().getScene().getWindow());
    }
}
