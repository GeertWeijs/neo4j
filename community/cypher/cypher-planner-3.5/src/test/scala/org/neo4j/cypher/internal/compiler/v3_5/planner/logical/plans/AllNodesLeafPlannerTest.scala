/*
 * Copyright (c) 2002-2019 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v3_5.planner.logical.plans

import org.neo4j.cypher.internal.compiler.v3_5.planner.LogicalPlanningTestSupport
import org.neo4j.cypher.internal.compiler.v3_5.planner.logical.ExpressionEvaluator
import org.neo4j.cypher.internal.compiler.v3_5.planner.logical.steps.allNodesLeafPlanner
import org.neo4j.cypher.internal.ir.v3_5.{QueryGraph, InterestingOrder}
import org.neo4j.cypher.internal.v3_5.logical.plans.AllNodesScan
import org.neo4j.cypher.internal.v3_5.expressions.PatternExpression
import org.neo4j.cypher.internal.v3_5.util.test_helpers.CypherFunSuite

class AllNodesLeafPlannerTest extends CypherFunSuite with LogicalPlanningTestSupport {

  private implicit val subQueryLookupTable = Map.empty[PatternExpression, QueryGraph]

  test("simple all nodes scan") {
    // given
    val queryGraph = QueryGraph(patternNodes = Set("n"))

    implicit val planContext = newMockedPlanContext
    val context = newMockedLogicalPlanningContext(planContext = planContext, metrics = newMockedMetricsFactory.newMetrics(hardcodedStatistics, mock[ExpressionEvaluator], config))

    // when
    val resultPlans = allNodesLeafPlanner(queryGraph, InterestingOrder.empty, context)

    // then
    resultPlans should equal(Seq(AllNodesScan("n", Set.empty)))
  }
}
