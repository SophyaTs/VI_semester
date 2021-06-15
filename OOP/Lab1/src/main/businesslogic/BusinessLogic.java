package main.businesslogic;

import java.util.List;
import java.util.Map;

import main.dao.DevelopingDAO;
import main.dao.TaskDAO;
import main.entities.Task;

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
