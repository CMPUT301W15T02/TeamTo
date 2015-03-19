package com.CMPUT301W15T02.teamtoapp;

/**
*
* Adapted from AndroidElasticSearch by Joshua Campbell 2015-03-18
*
* @see https://github.com/joshua2ua/AndroidElasticSearch
*
*
* @author Michael Stensby, Christine Shaffer, Kyle Carlstrom, Mitchell Messerschmidt, Raman Dhatt, Adam Rankin
*
*/

public class SimpleSearchCommand {
	private SimpleSearchQuery query;
		
	public SimpleSearchCommand(String query) {
		super();
		this.query = new SimpleSearchQuery(query);
	}

	public SimpleSearchCommand(String query, String[] fields) {
		super();
		throw new UnsupportedOperationException("Fields not yet implemented.");
	}

	public SimpleSearchQuery getQuery() {
		return query;
	}

	public void setQuery(SimpleSearchQuery query) {
		this.query = query;
	}

	static class SimpleSearchQuery {
		private SimpleSearchQueryString queryString;
		
		public SimpleSearchQuery(String query) {
			super();
			this.queryString = new SimpleSearchQueryString(query);
		}

		public SimpleSearchQueryString getQueryString() {
			return queryString;
		}

		public void setQueryString(SimpleSearchQueryString queryString) {
			this.queryString = queryString;
		}

		static class SimpleSearchQueryString {
			private String query;
			
			public SimpleSearchQueryString(String query) {
				super();
				this.query = query;
			}

			public String getQuery() {
				return query;
			}

			public void setQuery(String query) {
				this.query = query;
			}
		}
	}
}