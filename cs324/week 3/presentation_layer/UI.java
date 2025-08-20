package presentation_layer;

import java.util.*;
import business_layer.*;

class UI{
	private BLcustomer cus;

	public UI(){
		cus = new BLcustomer();
	}

	private int choice;

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public void add_customer(){
		Scanner in = new Scanner(System.in);

		System.out.println("Enter id: ");
		cus.setCusId(in.next());

		System.out.println("Enter last name: ");
		cus.setLName(in.next());
		System.out.println("Enter first name: ");
		cus.setFName(in.next());

		try{
			cus.add();
			System.out.println("addition successful");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void delete_customer(){
		Scanner in = new Scanner(System.in);

		System.out.println("Enter id: ");
		cus.setCusId(in.next());

		try{
			cus.delete();
			System.out.println("addition successful");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void update_customer(){
		Scanner in = new Scanner(System.in);

		System.out.println("Enter id: ");
		cus.setCusId(in.next());

		System.out.println("Enter first name: ");
		cus.setFName(in.next());

		System.out.println("Enter last name: ");
		cus.setLName(in.next());

		try{
			cus.update();
			System.out.println("addition successful");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void print(){
		Scanner in = new Scanner(System.in);

		System.out.println("press 1 to add a customer");
		System.out.println("press 2 to delete a customer");
		System.out.println("press 3 to update a customer");
		System.out.println("press 4 to exit an application");
		choice = in.nextInt();

		if (choice == 1){
			this.add_customer();
		}
		else if (choice == 2){
			this.delete_customer();
		}
		else if (choice == 3){
			this.update_customer();
		}
		else if (choice ==4){
			System.exit(1);
		}
		else{
			System.exit(1);
		}
	}
}
