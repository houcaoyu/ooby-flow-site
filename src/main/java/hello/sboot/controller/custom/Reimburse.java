package hello.sboot.controller.custom;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Reimburse {
	@Id
	private String processId;
	private String itemName;
	private String amount;
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
