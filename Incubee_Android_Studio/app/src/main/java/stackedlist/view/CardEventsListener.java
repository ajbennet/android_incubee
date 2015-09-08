package stackedlist.view;

public interface CardEventsListener {
	
	public void cardLiked(int position);
	
	public void cardDisliked(int position);
	
	public void onCardClicked(int position);

}
