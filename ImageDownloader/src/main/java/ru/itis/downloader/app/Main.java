package ru.itis.downloader.app;

import ru.itis.downloader.utils.*;
import com.beust.jcommander.*;

public class Main {
	public static void main(String[] argv){
		Downloader downloader = new Downloader();
		Args args = new Args();
		JCommander.newBuilder()
  			.addObject(args)
  			.build()
  			.parse(argv);
		if(!args.multyThreadMode){
			for(String uri : args.files){
				downloader.downloadByURI(uri, args.folder);
			}
		}
		else{
			ThreadPool threadPool = new ThreadPool(args.count);
			for(String uri : args.files){
				threadPool.submit(() -> {
					Downloader d = new Downloader();
					d.downloadByURI(uri, args.folder);
				});
			}
			threadPool.shutdownOnEmptyQueue();
		}
	}
}