package com.spider.myProcessor;

import java.util.List;

import javax.management.JMException;

import com.spider.util.ImageToLocalPipeline;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * PageProcessor负责解析页面，抽取有用信息，以及发现新的链接
 * 
 * @author Innodev-E531
 *
 */
public class RunoobProcessor implements PageProcessor {
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	// 重试次数为3次，抓取间隔为一秒
	private Site site = Site.me().setUserAgent("Spider").setRetryTimes(3).setSleepTime(1000);

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {
		Selectable s = page.getHtml();
		List<String> list = s.regex("http:\\/\\/static\\.runoob\\.com\\/images\\/icon\\/\\w{0,10}\\.png").all();
		// 部分二：定义如何抽取页面信息，并保存下来
		page.putField("list", list);
		// 部分三：从页面发现后续的url地址来抓取
		
//		page.addTargetRequests(list);
		System.err.println(list);
	}

	@Override
	public Site getSite() {
		if (site == null) {
			site = Site.me().setDomain("www.runoob.com/").setSleepTime(100).setRetryTimes(3);
		}
		return site;
	}

	public static void main(String[] args) {
		Spider itEye = Spider.create(new RunoobProcessor())
				// 从http://yanghaoli.iteye.com/开始抓
				.addUrl("http://www.runoob.com/")
				// D:\Data\webmagic 文件保存地址
				.addPipeline(new ImageToLocalPipeline("C:\\Users\\Innodev-E531\\Desktop\\我的网站图片\\png"))
//				.setDownloader(new ImageDownloader());
				;
		// 开启1个线程同时执行
		// .thread(1)
		// 启动爬虫
		// .run();

		try {
			// 添加监控
			SpiderMonitor.instance().register(itEye);
		} catch (JMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		itEye.start();
	}
}