package net.idea.examples.toxtree;

import ambit2.smarts.query.AbstractSmartsPattern;
import toxTree.core.IDecisionMethod;
import toxTree.core.IDecisionRule;
import toxTree.core.IImplementationDetails;
import toxTree.core.IProcessRule;
import toxTree.exceptions.DecisionMethodIOException;
import toxTree.tree.AbstractTreeWriter;
import toxTree.tree.DecisionNode;
import toxTree.tree.rules.smarts.AbstractRuleSmartSubstructure;
import toxtree.plugins.ames.AmesMutagenicityRules;

public class RetrieveImplementationDetails {

	public static void main(String[] args) {
		try {
			IProcessRule processor = new AbstractTreeWriter(System.out) {
				public void init(IDecisionMethod method) throws DecisionMethodIOException {

				}

				public Object process(IDecisionMethod method, IDecisionRule rule) throws DecisionMethodIOException {
					
					IImplementationDetails details = null;
					IDecisionRule irule = rule;
					if (rule instanceof IImplementationDetails)
						details = (IImplementationDetails) rule;
					if ((rule instanceof DecisionNode) 
							&& (((DecisionNode) rule).getRule() instanceof IImplementationDetails)) {
						irule =  ((DecisionNode) rule).getRule();
						details = (IImplementationDetails) irule;
					}
					try {

						getWriter().write(rule.getID());
						getWriter().write(".");
						getWriter().write(rule.getTitle());
						if (irule instanceof AbstractRuleSmartSubstructure) {
							getWriter().write("\tSMARTS: YES");	
						} else 
							getWriter().write("\tSMARTS: NO");	
						getWriter().write("\n");
						if (details != null) {
							getWriter().write(details.getImplementationDetails());
							getWriter().write("\n");
						}
						getWriter().write("\n");
					} catch (Exception x) {
						x.printStackTrace();
					}
					return method;
				};
			};

			IDecisionMethod tree = new AmesMutagenicityRules();
			/*
			IDecisionMethod tree = new CramerRules();
			IDecisionMethod tree = new CramerRulesWithExtensions();
			IDecisionMethod tree = new DNABindingPlugin();
			IDecisionMethod tree = new EyeIrritationRules();
			IDecisionMethod tree = new SicretRules(); //skin irritation
			IDecisionMethod tree = new ProteinBindingPlugin();
			IDecisionMethod tree = new BiodegradationRules();
			IDecisionMethod tree = new MICRules();
			IDecisionMethod tree = new BBCarcMutRules();
			*/
			tree.walkRules(tree.getTopRule(), processor);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

}
