package com.red.thread;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.red.util.DataUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HomePageRoomExecutor extends Thread {

	private static JsonParser parser = new JsonParser();
	
	public void run(){
		while(true){
			try {
				Set<Integer> set = jsopParse();
				if(set.size() > 0){
					for(Integer roomId : set){
						DataUtil.ROOM_QUEUE.add(roomId.toString());
					}
				}
				Thread.sleep(15 * 60 * 1000); //每隔15分钟取一次首页房间
			} catch (InterruptedException e) {
				e.printStackTrace();
				e.printStackTrace();
			}
		}
	}

	public static Set<Integer> jsopParse(){
		Set<Integer> set = new HashSet<Integer>();
		try {
			Document doc = Jsoup.connect("http://www.kktv5.com/").get();
			Elements wrap = doc.getElementsByClass("wrap");
			for(Element ele : wrap){
				Elements links = ele.getElementsByTag("a");
				for (Element link : links) { 
					String roomId = link.attr("data-id");
					if(roomId != null && !"".equals(roomId)){
						set.add(Integer.valueOf(roomId));
					}
				}
			}
			
			Elements banner_right = doc.getElementsByClass("banner_right");
			for(Element ele : banner_right){
				Elements links = ele.getElementsByTag("a");
				for (Element link : links) { 
					String roomId = link.attr("href");
					if(roomId != null && !"".equals(roomId)){
						set.add(Integer.valueOf(roomId.substring(1,roomId.length())));
					}
				}
			}
			
			Elements banner_bottom = doc.getElementsByClass("banner_bottom");
			for(Element ele : banner_bottom){
				Elements links = ele.getElementsByTag("a");
				for (Element link : links) { 
					String roomId = link.attr("href");
					if(roomId != null && !"".equals(roomId)){
						set.add(Integer.valueOf(roomId.substring(1,roomId.length())));
					}
				}
			}
			
			Elements script = doc.getElementsByTag("script");
			String data = script.get(2).data().trim();
			String[] str = data.split("=");
			JsonObject choiceActor = (JsonObject)parser.parse(str[1].substring(str[1].indexOf("{"), str[1].lastIndexOf("}")+1));
			JsonArray plateList = (JsonArray)parser.parse(str[2].substring(str[2].indexOf("["), str[2].lastIndexOf("]")+1));
			JsonArray recommendRoomList = (JsonArray)parser.parse(str[3].substring(str[3].indexOf("["), str[3].lastIndexOf("]")+1));
			JsonArray posterList = choiceActor.get("posterList").getAsJsonArray();
			for(JsonElement ele : posterList){
				JsonObject json = (JsonObject)ele;
				int actorId = json.get("actorId").getAsInt();
				set.add(actorId);
			}
			for(JsonElement ele : plateList){
				JsonObject json = (JsonObject)ele;
				JsonArray array = json.get("result").getAsJsonArray();
				for(JsonElement child : array){
					JsonObject obj = (JsonObject)child;
					int roomId = obj.get("roomId").getAsInt();
					set.add(roomId);
				}
			}
			for(JsonElement ele : recommendRoomList){
				JsonObject json = (JsonObject)ele;
				int roomId = json.get("roomId").getAsInt();
				set.add(roomId);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return set;
	}

	public static void main(String[] args) {
		Set<Integer> set = jsopParse();
		System.out.println(set);
	}
}
