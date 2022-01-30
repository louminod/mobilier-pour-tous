package entitiees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Archive {

	private List<Don> lstDonsArchives;

	public Archive() {
		this.lstDonsArchives = new ArrayList<>();
	}

	public List<Don> getLstDonsArchives() {
		return Collections.unmodifiableList(this.lstDonsArchives);
	}

	public void archiveDon(Don don) {
		this.lstDonsArchives.add(don);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
