package com.CMPUT301W15T02.teamtoapp.test;

import java.util.ArrayList;

import com.CMPUT301W15T02.teamtoapp.User;
import com.CMPUT301W15T02.teamtoapp.UserController;

public class ClaimListTest {
	UserController.getInstance().addUser(new User("John"));
	ArrayList<User> users = UserController.getInstance().getUsers();
}
