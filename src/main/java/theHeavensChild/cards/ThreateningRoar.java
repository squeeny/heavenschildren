package theHeavensChild.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theHeavensChild.util.Wiz;

import static theHeavensChild.HeavensChildMod.makeID;

public class ThreateningRoar extends AbstractEasyCard {
    public final static String ID = makeID("ThreateningRoar");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,
    private final int BASE_DRAW = 2;
    private final int UPG_DRAW = 1;

    private static final int COST = 1;


    public ThreateningRoar() {
        super(ID, COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = BASE_DRAW;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber, new AbstractGameAction() {

            @Override
            public void update() {
                int attacksDrawn = 0;
                for (AbstractCard c : DrawCardAction.drawnCards) {
                    if (c.type == CardType.ATTACK) {
                        attacksDrawn++;
                    }
                }
                if (attacksDrawn > 0 ) {
                    for (AbstractMonster mon : Wiz.getEnemies()) {
                        Wiz.applyToEnemy(mon, new VulnerablePower(mon, attacksDrawn, false));
                    }
                }
                this.isDone = true;
            }
        }));
    }

    public void upgradeVariables() {
        upgradeMagicNumber(UPG_DRAW);
    }
}