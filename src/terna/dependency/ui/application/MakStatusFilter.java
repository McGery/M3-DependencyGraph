package terna.dependency.ui.application;

import terna.dependency.logic.GraphNode;

public class MakStatusFilter {

	public static final MakStatusFilter SHOW_ALL = new MakStatusFilter();
	
	private boolean showDevelopmentNumbers;
	private boolean showTestNumbers;
	private boolean showApprovedNumbers;
	private boolean showExportedNumbers;
	
	public MakStatusFilter() {
		showDevelopmentNumbers = true;
		showTestNumbers = true;
		showApprovedNumbers = true;
		showExportedNumbers = true;
	}
	
	public boolean isShowDevelopmentNumbers() {
		return showDevelopmentNumbers;
	}
	public void setShowDevelopmentNumbers(boolean showDevelopmentNumbers) {
		this.showDevelopmentNumbers = showDevelopmentNumbers;
	}
	public boolean isShowTestNumbers() {
		return showTestNumbers;
	}
	public void setShowTestNumbers(boolean showTestNumbers) {
		this.showTestNumbers = showTestNumbers;
	}
	public boolean isShowApprovedNumbers() {
		return showApprovedNumbers;
	}
	public void setShowApprovedNumbers(boolean showApprovedNumbers) {
		this.showApprovedNumbers = showApprovedNumbers;
	}
	public boolean isShowExportedNumbers() {
		return showExportedNumbers;
	}
	public void setShowExportedNumbers(boolean showExportedNumbers) {
		this.showExportedNumbers = showExportedNumbers;
	}

	public void setValues(MakStatusFilter filter) {
		setShowDevelopmentNumbers(filter.isShowDevelopmentNumbers());
		setShowTestNumbers(filter.isShowTestNumbers());
		setShowApprovedNumbers(filter.isShowApprovedNumbers());
		setShowExportedNumbers(filter.isShowExportedNumbers());
	}
	
	public boolean isValid(GraphNode node) {
		boolean valid = false;
		if (isShowDevelopmentNumbers()) {
			valid |= node.getMakStatus().equals("development");
		}
		if (isShowTestNumbers()) {
			valid |= node.getMakStatus().equals("test");
		}
		if (isShowApprovedNumbers()) {
			valid |= node.getMakStatus().equals("approved");
		}
		if (isShowExportedNumbers()) {
			valid |= node.getMakStatus().equals("exported");
		}
		return valid;
	}
}
