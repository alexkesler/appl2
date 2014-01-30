package org.kesler.appl2.gui.reception.make;

import java.util.List;
import java.util.ArrayList;

public class MakeReceptionViewStateModel {

	private MakeReceptionViewController controller;
	private MakeReceptionView view;
	private List<MakeReceptionViewState> states;

	public MakeReceptionViewStateModel(MakeReceptionViewController controller, MakeReceptionView view) {
		this.controller = controller;
		this.view = view;
		states = new ArrayList<MakeReceptionViewState>();
		initStates();
	}

	public int getStatesCount() {
		return states.size();
	}

	public MakeReceptionViewState getState(int index) {
		return states.get(index);
	}

	private void initStates() {
		states.add(new ServiceMakeReceptionViewState(controller, view));
	}

}