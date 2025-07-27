package managers;

import dataProvider.ConfigFileReader;

public class FileReaderManager {

    private FileReaderManager(){}

    private static class Holder{
        private static final FileReaderManager INSTANCE = new FileReaderManager();

        private static ConfigFileReader configFileReader;
    }

    public static FileReaderManager getInstance(){
        return Holder.INSTANCE;
    }

    public static ConfigFileReader getConfigReader(){
        if(Holder.configFileReader == null){
            Holder.configFileReader = new ConfigFileReader();
        }
        return Holder.configFileReader;
    }
}
