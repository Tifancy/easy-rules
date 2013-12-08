/*
 * The MIT License
 *
 *  Copyright (c) 2013, benas (md.benhassine@gmail.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.github.easyrules.core;

import io.github.easyrules.api.Rule;
import io.github.easyrules.util.EasyRulesConstants;

import java.util.Set;
import java.util.TreeSet;

/**
 * Class representing a composite rule composed of a set of rules.<br/>
 * A composite rule is triggered if <strong>ALL</strong> conditions of its composing rules are satisfied.
 * When a composite rule is applied, actions of <strong>ALL</strong> composing rules are performed
 * in the natural order of rules which is rules priorities by default.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class CompositeRule extends BasicRule {

    /**
     * The set of composing rules.
     */
    private Set<Rule> rules;

    public CompositeRule() {
        this(EasyRulesConstants.DEFAULT_RULE_NAME,
                EasyRulesConstants.DEFAULT_RULE_DESCRIPTION,
                EasyRulesConstants.DEFAULT_RULE_PRIORITY);
        rules = new TreeSet<Rule>();
    }

    public CompositeRule(String name, String description, int priority) {
        super(name, description, priority);
        rules = new TreeSet<Rule>();
    }

    /**
     * A composite rule is triggered if <strong>ALL</strong> conditions of all composing rules are evaluated to true.
     * @return true if <strong>ALL</strong> conditions of composing rules are evaluated to true
     */
    @Override
    public boolean evaluateConditions() {
        if (!rules.isEmpty()){
            for (Rule rule : rules) {
                if (!rule.evaluateConditions()){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * When a composite rule is applied, <strong>ALL</strong> actions of composing rules are performed in the order
     * of rules priorities
     *
     * @throws Exception thrown if an exception occurs during actions performing
     */
    @Override
    public void performActions() throws Exception {
        for (Rule rule : rules) {
            rule.performActions();
        }
    }

    /**
     * Add a rule to the composite rule.
     * @param rule the rule to add
     */
    public void addRule(Rule rule) {
        rules.add(rule);
    }

    /**
     * Remove a rule from the composite rule.
     * @param rule the rule to remove
     */
    public void removeRule(Rule rule) {
        rules.remove(rule);
    }

}