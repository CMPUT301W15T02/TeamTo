package com.CMPUT301W15T02.teamtoapp;

import java.util.List;

/**
*
* Adapted from AndroidElasticSearch by Joshua Campbell
*
* @see https://github.com/joshua2ua/AndroidElasticSearch
*
*
* @author Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
*
* @param <T>
*/

public class Hits<T> {
	private int total;
	private float max_score;
	private List<SearchHit<T>> hits;
	
	public Hits() {}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public float getMax_score() {
		return max_score;
	}

	public void setMax_score(float max_score) {
		this.max_score = max_score;
	}

	public List<SearchHit<T>> getHits() {
		return hits;
	}

	public void setHits(List<SearchHit<T>> hits) {
		this.hits = hits;
	}

	@Override
	public String toString() {
		return "Hits [total=" + total + ", max_score=" + max_score + ", hits="
				+ hits + "]";
	}
}