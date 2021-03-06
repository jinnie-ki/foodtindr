package food.gamestate;

import food.YelpAPI;

import java.util.ArrayList;

public class GameStateManager {

    public static GameStateManager gameStateManager = new GameStateManager();

    ArrayList<Room> gameRoom = new ArrayList<Room>();

    private GameStateManager(){

        // TODO: 11/22/2020 REMOVE LATER
        Room newGame = new Room();
        newGame.roomCode = "ABCD";
        newGame.addPlayer("TINA");
        newGame.addPlayer("colin");
        gameRoom.add(newGame);
    }

    public String createNewGame(String playerName){
        Room newGame = new Room();
        String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuffer newGameCode = new StringBuffer(6);//Creates StringBuffer of size 4

        /*
         * for the length of the StringBuffer newGameCode, randomly chooses a character
         * in the String randomString and appends it to newGameCode
         */
        for(int i = 0; i < 6; i++){
            int index = (int)(Math.random() * randomString.length());
            newGameCode.append(randomString.charAt(index));
        }
        //Sets the Room gameRoom's code to newGameCode
        newGame.roomCode = newGameCode.toString();
        newGame.addPlayer(playerName);
        gameRoom.add(newGame);

        return newGameCode.toString();
    }

    public void addPlayers(String roomCode, String playerName){

        for(Room game: gameRoom){
            if(game.roomCode.equals(roomCode)){
                game.addPlayer(playerName);
                return;
            }
        }
        throw new RuntimeException("Can't find room");
    }

    public void startGame(String roomCode){
        for(Room game: gameRoom){
            if(game.roomCode.equals(roomCode)){
                ArrayList<YelpAPI.Restaurant> listRestaurant = YelpAPI.getMeRestaurant();
                for(YelpAPI.Restaurant restaurant: listRestaurant){
                    game.restaurantMap.put(restaurant.restaurantName, 0);
                }
                game.restaurantToImage = listRestaurant;
                game.gameStarted = true;
                return;
            }
        }

    }

    public void confirmRestaurants(String roomCode, String playerName, String restaurant){
        for(Room game: gameRoom){
            if(game.roomCode.equals(roomCode)){
                Integer temp = game.restaurantMap.get(restaurant);
                temp++;
                game.restaurantMap.put(restaurant,temp);
                if(temp == game.players.size()){
                    game.winner = true;
                }
            }
        }
    }

//    Object getGameState(){
//        Object hi = new Object();
//
//        return hi;
//    }

    public Room getRoom(String roomCode){
        for(Room game: gameRoom){
            if(game.roomCode.equals(roomCode)){
                return game;
            }
        }
        throw new RuntimeException("Runtime Error");
    }


}
