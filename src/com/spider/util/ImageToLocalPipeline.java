package com.spider.util;

import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

public class ImageToLocalPipeline extends FilePersistentBase implements Pipeline {

	public ImageToLocalPipeline(String path) {
		setPath(path);
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		List<String> list = resultItems.get("list");
		String path = this.path + task.getUUID() + "/";
		for(String url : list){
			HttpUtil.createPng(path, url);
		}
	}

}
