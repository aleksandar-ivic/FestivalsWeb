package rs.fon.is.festivals.domain;

import java.util.Date;

import rs.fon.is.festivals.util.Constants;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
import thewebsemantic.RdfType;

@Namespace(Constants.TL)
@RdfType("Interval")
public class Interval extends Thing {

	@RdfProperty(Constants.TL + "start")
	private Date start;

	@RdfProperty(Constants.TL + "end")
	private Date end;

	public Interval() {

	}

	public Interval(Date start, Date end) {
		this.start = start;
		this.end = end;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return start + "-" + end;
	}

}
