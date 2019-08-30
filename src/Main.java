import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main
{
    public static void main(String[] args)
    {
        //================================ INIT ======================================================
        int targetWidth = 300;
        Path srcFolder = Paths.get("images\\src"); // источник фото
        String dstFolder = "images\\dst";// назначение
        int coreCount = Runtime.getRuntime().availableProcessors();// количество ядер
        Integer index;
        HashMap<Integer,List<File>> threadPool = new HashMap<>();// схема распределения файлов по потокам
        // ============================================================================================
        try {
            List<Path> fileList = Files.list(srcFolder).collect(Collectors.toList());// список файлов
            int filesCount = fileList.size(); // количество файлов
            int stackSize =  (filesCount / coreCount) + 1; // количество файлов на поток
            // ========= Задачка про ящики контейнеры и грузовики :) =======================
            for (int i = 0;i < filesCount;i++)
            {
                index = i / stackSize;
                if (i % stackSize == 0)
                {
                   threadPool.put(index, new ArrayList<>());
                   threadPool.get(index).add(fileList.get(i).toFile());
                }
                else threadPool.get(index).add(fileList.get(i).toFile());
            }
            long start = System.currentTimeMillis();
            threadPool.forEach((ind, list) -> new ImageResizer(list,targetWidth,dstFolder,start,ind).start());
        }
        catch (Exception ex){
            ex.printStackTrace();}

    }
}
