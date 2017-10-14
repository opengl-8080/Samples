package since9.html;

import org.jsoup.nodes.Element;

public class MemberBlock {
    private final ClassHtml owner;
    private final Element ulBlockList;
    private SummaryBlock summaryBlock;

    public MemberBlock(ClassHtml owner, Element ulBlockList) {
        this.owner = owner;
        this.ulBlockList = ulBlockList;
    }
    
    public String name() {
        this.cacheSummary();
        return this.summaryBlock.name();
    }
    
    public String description() {
        this.cacheSummary();
        return this.summaryBlock.description();
    }

    public boolean isSince9() {
        return this.ulBlockList.select("dl").text().contains("Since: 9");
    }
    
    public String id() {
        return this.ulBlockList.previousElementSibling().id();
    }
    
    private void cacheSummary() {
        if (this.summaryBlock != null) {
            return;
        }
        
        this.summaryBlock = this.owner.findSummaryBlock(this.id());
    }

    @Override
    public String toString() {
        return "MemberBlock(name=" + this.ulBlockList.select("h4").text() + ", id=" + this.id() + ")";
    }
}
