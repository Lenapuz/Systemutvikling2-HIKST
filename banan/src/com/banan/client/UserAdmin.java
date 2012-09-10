package com.banan.client;

import java.lang.reflect.Array;

import com.banan.shared.Profile;
import com.banan.shared.SimResult;
import com.banan.shared.User;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserAdmin extends Composite 
{
	private VerticalPanel panel;
	
	
	public UserAdmin()
	{
		panel = new VerticalPanel();
		panel.addStyleName("userAdmin");
		initWidget(panel);

		Main.UserService.GetUsers(
				new AsyncCallback<User[]>() 
				{
					@Override
					public void onFailure(Throwable caught) 
					{
						Window.alert(caught.getMessage());
						
					}

					@Override
					public void onSuccess(User[] result) 
					{
						int row = 0;
						
						FlexTable flextable = new FlexTable();
						Button b;
						
						flextable.setWidget(row, 0, new Label("Fultnavn:"));
						flextable.setWidget(row, 1, new Label("Brukernavn:"));
						flextable.setWidget(row, 2, new Label("Type:"));
						flextable.setWidget(row, 3, new Label("Slett:"));
						flextable.setWidget(row, 4, new Label("Edit:"));
						
						for(User u : result)
						{
							row++;
							flextable.setWidget(row, 0, new Label(u.getName()));
							flextable.setWidget(row, 1, new Label(u.getUsername()));
							flextable.setWidget(row, 2, new Label(u.getType()));
							b = new Button();
							b.setText("Edit");
							flextable.setWidget(row, 3, b);
							b = new Button();
							b.setText("Slett");
							flextable.setWidget(row, 4, b);
						}
						panel.add(flextable);
					}
				});
	}
}
