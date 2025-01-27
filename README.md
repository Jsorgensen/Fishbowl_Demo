## Overview
We would like you to create a simple Android application that makes network calls to a [joke API](https://v2.jokeapi.dev) and displays them in a list. Additionally, the app should be able to view the joke on a detail screen.

The main goal is to make sure the app can fetch from the api and display them in a list and detail view. So it would be best to focus on functionality of the app the most and then proper theming second. So when you are ready, create a new Android project in Android Studio and dive in!

Note: All features relating to being able save favorited jokes locally are bonus features that you can do if you have the time or are eager to dive into them.

## Design
Before you get started, please check out the [design](https://xd.adobe.com/view/fbb507ff-d752-456d-8cb2-a61ac1b923a8-aee6/) to get a sense for the app's look and feel, get assets, colors, and styles.

Note: To download assets such as the joke category icons use the `</>` button on the right toolbar when on the `Styles` page. This can also be used on other screens so you can inspect things like spacings, fonts, etc. 

## API Note
Make sure to use the `safe-mode` parameter when using the API to make sure things are SFW. See the [section on safe mode](https://v2.jokeapi.dev/#safe-mode) for more details.

## Feature Specs
### Joke List Screen
- [Design](https://xd.adobe.com/view/fbb507ff-d752-456d-8cb2-a61ac1b923a8-aee6/screen/cd033a46-d331-4f13-b3cf-cb658498f6a3/specs/)
- When the app launches the joke list screen should be displayed
- Top App Bar:
	- Title: "Jokes"
	- Search bar:
		- A search textfield should be displayed below the title
			- Placeholder: "Search Jokes"
		- When the "Search" button is clicked: Refresh the list applying the text as a search query.
	- Filter Button: 
		- On Click: displays dropdown of joke categories
			- Dropdown shows selected category highlighted, or Any if none are selected.
			- On selection: 
				- Refresh the list with jokes filtered by selected category
				- Change the icon of the button in the top app bar to reflect the selected category
	- Bonus:
		- Favorites Button:
		- On Click: Navigates to saved favorites screen (see below)
- Screen Content:
	- Each joke should be represented by the joke row component in the design
	- The screen should fetch and display a page of jokes
		- Page size: 25
		- Bonus: When scrolling to the bottom of the list the app fetches another 25 jokes (infinite scrolling)
	- Bonus: When the screen is loading jokes placeholder rows are shown to indicate loading to the user

### Joke Detail Screen
- [Design](https://xd.adobe.com/view/fbb507ff-d752-456d-8cb2-a61ac1b923a8-aee6/screen/641f971d-9410-46aa-bda6-bac3ed127bc7/specs/)
- Top App Bar:
	- Title: "Joke"
	- Nav Button: Left Chevron
		- Navigates back to the Joke List Screen
- Screen Content:
	- The screen displays the joke and category that was selected on the list screen
		- Design defined by the Joke Detail Component
	- Bonus: Clicking on the heart button saves the joke to the favorites list that persists across app launches
		- When the joke is favorited show the `heart` icon, and when not favorited show the `heart-outline` icon

## Favorite Jokes Screen (Bonus)
- [Design](https://xd.adobe.com/view/fbb507ff-d752-456d-8cb2-a61ac1b923a8-aee6/screen/86d9fde1-651e-4185-8ef1-7b06adc177e6/specs/) 
- Top App Bar:
	- Title: "Favorites"
	- Nav Button: Left Chevron
		- Navigates back to the Joke List Screen
- Screen Content:
	- Each joke should be represented by the Joke Row component in the design
	- Swiping on the row reveals a "unfavorite" action:
		- Finishing the action removes the joke from the favorites list and removes the swiped row
