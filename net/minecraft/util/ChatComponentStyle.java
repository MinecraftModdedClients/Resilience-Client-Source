package net.minecraft.util;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public abstract class ChatComponentStyle implements IChatComponent
{
    /**
     * The later siblings of this component.  If this component turns the text bold, that will apply to all the siblings
     * until a later sibling turns the text something else.
     */
    protected List siblings = Lists.newArrayList();
    private ChatStyle style;
    private static final String __OBFID = "CL_00001257";

    /**
     * Appends the given component to the end of this one.
     */
    public IChatComponent appendSibling(IChatComponent p_150257_1_)
    {
        p_150257_1_.getChatStyle().setParentStyle(this.getChatStyle());
        this.siblings.add(p_150257_1_);
        return this;
    }

    /**
     * Gets the sibling components of this one.
     */
    public List getSiblings()
    {
        return this.siblings;
    }

    /**
     * Appends the given text to the end of this component.
     */
    public IChatComponent appendText(String p_150258_1_)
    {
        return this.appendSibling(new ChatComponentText(p_150258_1_));
    }

    public IChatComponent setChatStyle(ChatStyle p_150255_1_)
    {
        this.style = p_150255_1_;
        Iterator var2 = this.siblings.iterator();

        while (var2.hasNext())
        {
            IChatComponent var3 = (IChatComponent)var2.next();
            var3.getChatStyle().setParentStyle(this.getChatStyle());
        }

        return this;
    }

    public ChatStyle getChatStyle()
    {
        if (this.style == null)
        {
            this.style = new ChatStyle();
            Iterator var1 = this.siblings.iterator();

            while (var1.hasNext())
            {
                IChatComponent var2 = (IChatComponent)var1.next();
                var2.getChatStyle().setParentStyle(this.style);
            }
        }

        return this.style;
    }

    public Iterator iterator()
    {
        return Iterators.concat(Iterators.forArray(new ChatComponentStyle[] {this}), createDeepCopyIterator(this.siblings));
    }

    /**
     * Gets the text of this component, without any special formatting codes added.  TODO: why is this two different
     * methods?
     */
    public final String getUnformattedText()
    {
        StringBuilder var1 = new StringBuilder();
        Iterator var2 = this.iterator();

        while (var2.hasNext())
        {
            IChatComponent var3 = (IChatComponent)var2.next();
            var1.append(var3.getUnformattedTextForChat());
        }

        return var1.toString();
    }

    /**
     * Gets the text of this component, with formatting codes added for rendering.
     */
    public final String getFormattedText()
    {
        StringBuilder var1 = new StringBuilder();
        Iterator var2 = this.iterator();

        while (var2.hasNext())
        {
            IChatComponent var3 = (IChatComponent)var2.next();
            var1.append(var3.getChatStyle().getFormattingCode());
            var1.append(var3.getUnformattedTextForChat());
            var1.append(EnumChatFormatting.RESET);
        }

        return var1.toString();
    }

    /**
     * Creates an iterator that iterates over the given components, returning deep copies of each component in turn so
     * that the properties of the returned objects will remain externally consistent after being returned.
     */
    public static Iterator createDeepCopyIterator(Iterable p_150262_0_)
    {
        Iterator var1 = Iterators.concat(Iterators.transform(p_150262_0_.iterator(), new Function()
        {
            private static final String __OBFID = "CL_00001258";
            public Iterator apply(IChatComponent p_150665_1_)
            {
                return p_150665_1_.iterator();
            }
            public Object apply(Object par1Obj)
            {
                return this.apply((IChatComponent)par1Obj);
            }
        }));
        var1 = Iterators.transform(var1, new Function()
        {
            private static final String __OBFID = "CL_00001259";
            public IChatComponent apply(IChatComponent p_150666_1_)
            {
                IChatComponent var2 = p_150666_1_.createCopy();
                var2.setChatStyle(var2.getChatStyle().createDeepCopy());
                return var2;
            }
            public Object apply(Object par1Obj)
            {
                return this.apply((IChatComponent)par1Obj);
            }
        });
        return var1;
    }

    public boolean equals(Object par1Obj)
    {
        if (this == par1Obj)
        {
            return true;
        }
        else if (!(par1Obj instanceof ChatComponentStyle))
        {
            return false;
        }
        else
        {
            ChatComponentStyle var2 = (ChatComponentStyle)par1Obj;
            return this.siblings.equals(var2.siblings) && this.getChatStyle().equals(var2.getChatStyle());
        }
    }

    public int hashCode()
    {
        return 31 * this.style.hashCode() + this.siblings.hashCode();
    }

    public String toString()
    {
        return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
    }
}
