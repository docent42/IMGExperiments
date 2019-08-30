import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import static org.imgscalr.Scalr.OP_ANTIALIAS;

public class ImageResizer extends Thread
{
    private List<File> files;
    private int targetWidth;
    private String dstFolder;
    private long start;
    private int index;

    ImageResizer(List<File> files, int targetWidth, String dstFolder,long start,int index) {
        this.files = files;
        this.targetWidth = targetWidth;
        this.dstFolder = dstFolder;
        this.start = start;
        this.index = index;
    }

    @Override
    public void run()
    {
        try
        {
            for(File file : files)
            {
                BufferedImage image = ImageIO.read(file);
                if(image == null) continue;

                BufferedImage newImage = createThumbnail(image,targetWidth);

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
            System.out.printf("Thread: %d - %.4f sec%n",index,((double)(System.currentTimeMillis() - start)/1000));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static BufferedImage createThumbnail(BufferedImage img, int targetSize)
    {
        img = Scalr.resize(img, Scalr.Method.SPEED, targetSize * 3);
        img = Scalr.resize(img, Scalr.Method.QUALITY, targetSize,OP_ANTIALIAS);
        return img;
    }
}
