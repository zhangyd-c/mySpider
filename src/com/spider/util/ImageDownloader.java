package com.spider.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import us.codecraft.webmagic.downloader.HttpClientDownloader;

public class ImageDownloader extends HttpClientDownloader {

	@Override
	protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
		HttpEntity entity = httpResponse.getEntity();
		byte[] imageByte = EntityUtils.toByteArray(entity);
		String iageStr = ImageBase64Utils.GetImageStr(imageByte);
		return iageStr;
	}
}
