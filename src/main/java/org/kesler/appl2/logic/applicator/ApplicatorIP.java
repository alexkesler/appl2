package org.kesler.appl2.logic.applicator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Proxy;

import org.kesler.appl2.logic.Applicator;
import org.kesler.appl2.logic.FL;
import org.kesler.appl2.logic.IP;

/**
* Класс представляет заявителя - ИП, привязанного к приему документов, включая информацию о представителе  
*/
@Entity
@Table (name = "ApplicatorsIP")
@PrimaryKeyJoinColumn(name="ID", referencedColumnName="ID")
@Proxy(lazy=false)
public class ApplicatorIP extends Applicator {
	
	@ManyToOne
	@JoinColumn(name="IPID")
	private IP ip;

	@ManyToOne
	@JoinColumn(name="RepresID")
	private FL repres;

	public ApplicatorIP() {}

	public IP getIP() {
		return ip;
	}

	public void setIP(IP ip) {
		this.ip = ip;
	} 

	@Override
	public FL getRepres() {
		return repres;
	}

	public void setRepres(FL repres) {
		this.repres = repres;
	}

	public String getRepresFIO() {
		String fio = "Не определен";

		if (repres!=null) fio = repres.getFIO();

		return fio;
	}	

	@Override
	public String getShortName() {
		String name = "Не определено";

		if (ip!=null) name = ip.getShortName();

		return name;
	}

	@Override
	public String getFullName() {
		String name = "Не определено";

		if (ip!=null) name = ip.getFullName();

		return name;
	}

	@Override
	public String toString() {
		String ipShortName = "";
		if (ip!=null) ipShortName = ip.getShortName();
		String represFIO = "";
		if (repres!=null) represFIO = " (" + repres.getFIO() + ")";

		return ipShortName + represFIO;

	}

    @Override
    public Applicator copyThis() {
        ApplicatorIP newAppilcatorIP = new ApplicatorIP();
        newAppilcatorIP.setIP(ip);
        newAppilcatorIP.setRepres(repres);
        return newAppilcatorIP;
    }
}