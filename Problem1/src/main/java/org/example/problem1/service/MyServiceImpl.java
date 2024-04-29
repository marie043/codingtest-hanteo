package org.example.problem1.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

import org.example.problem1.dto.Cat;
import org.example.problem1.dto.Relation;
import org.example.problem1.vo.BoardOutputVo;

@org.springframework.stereotype.Service
public class MyServiceImpl implements MyService {

	private Map<Integer, Cat> categories;
	private List<Relation> relations;

	public void addBoard() {
		String[] input = {
			"남자 엑소 공지사항",
			"남자 엑소 첸",
			"남자 엑소 백현",
			"남자 엑소 시우민",
			"남자 방탄소년단 공지사항",
			"남자 방탄소년단",
			"남자 방탄소년단 뷔",
			"여자 블랙핑크 공지사항",
			"여자 블랙핑크",
			"여자 블랙핑크 로제"
		};
		StringTokenizer tok;
		int lastId = 0;
		categories = new HashMap<>();
		relations = new ArrayList<>();
		for(String s : input){
			tok = new StringTokenizer(s);
			String c1 = tok.nextToken(), c2 = tok.nextToken(), c3 = null;
			if(tok.hasMoreTokens()){
				c3 = tok.nextToken();
			}
			int i1 = -1, i2 = -1, i3 = -1;
			for(Integer i : categories.keySet()){
				if(categories.get(i).getName().equals(c1)){
					i1 = i;
					break;
				}
			}
			if(i1 == -1){
				i1 = categories.size();
				categories.put(i1,new Cat(c1, null, 0));
			}
			for(Relation r : relations){
				if(r.getParentIdx() == i1){
					if(categories.get(r.getChidId()).equals(c2)){
						i2 = r.getChidId();
						break;
					}
				}
			}
			if(i2 == -1){
				i2 = categories.size();
				categories.put(i2, new Cat(c2, null, 0));
				relations.add(new Relation(i1, i2));
			}
			if(c3 == null){
				for(Integer i : categories.keySet()){
					if(categories.get(i).getName().equals("익명게시판")){
						i3 = i;
						break;
					}
				}
				if(i3 == -1){
					i3 = categories.size();
					categories.put(i3, new Cat("익명게시판", "익명게시판", ++lastId));
				}
				relations.add(new Relation(i2, i3));
			}else{
				i3 = categories.size();
				categories.put(i3, new Cat(c3, c1+ " " + c2 + " " + c3, ++lastId));
				relations.add(new Relation(i2, i3));
			}
		}
	}

	@Override
	public List listBoard(String vo) {
		if(categories == null || relations == null){
			addBoard();
		}
		List<BoardOutputVo> data = new ArrayList<>();
		for(Integer i : categories.keySet()){
			if(vo.equals(categories.get(i).getName())){
				int idx = i;
				Queue<Relation> qr = new ArrayDeque<>();
				for(Relation r : relations){
					if(r.getParentIdx() == i){
						qr.add(r);
					}
				}
				while(qr.size() > 0){
					Relation r = qr.poll();
					int childIdx = r.getChidId();
					if(categories.get(childIdx).getAnswer() !=null){
						data.add(new BoardOutputVo(categories.get(childIdx).getAnswer(), categories.get(childIdx).getId()));
					}else{
						for(Relation rr : relations){
							if(rr.getParentIdx() == childIdx){
								qr.add(rr);
							}
						}
					}
				}
			}
		}
		return data;
	}
}
