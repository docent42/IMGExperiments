import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import static org.imgscalr.Scalr.OP_ANTIALIAS;

public class Main
{
    public static void main(String[] args)
    {
        String srcFolder = "G:\\Java_work\\Block11\\ImgExperiments\\images\\src";
        String dstFolder = "G:\\Java_work\\Block11\\ImgExperiments\\images\\dst";
        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        try
        {
            for(File file : files)
            {
                BufferedImage image = ImageIO.read(file);
                if(image == null) {
                    continue;
                }
                BufferedImage newImage = createThumbnail(image,300);

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Duration: " + (System.currentTimeMillis() - start));
    }
    private static BufferedImage createThumbnail(BufferedImage img, int targetSize)
    {
        img = Scalr.resize(img, Scalr.Method.SPEED, targetSize * 3);// OP_ANTIALIAS
        img = Scalr.resize(img, Scalr.Method.QUALITY, targetSize,OP_ANTIALIAS);
        return img;
    }
}
