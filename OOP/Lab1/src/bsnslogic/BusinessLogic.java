package bsnslogic;

import java.util.List;
import java.util.Map;

import dao.DevelopingDAO;
import dao.TasksDAO;
import entities.Task;

public class BusinessLogic {
	public static long calculateCost(final long projectId) {
		List<List<Long>> info = DevelopingDAO.getCostAndHoursByProjectId(projectId);
		long cost = 0;
		for(int i=0; i< info.size(); ++i) {
			cost += info.get(i).get(0)*info.get(i).get(1);
		}
		return cost;
	}
}
