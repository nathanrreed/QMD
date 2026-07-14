package lach_01298.qmd.recipe;

import com.google.common.base.CaseFormat;
import com.nred.nuclearcraft.recipe.SimpleRecipeBuilder;
import lach_01298.qmd.QMD;
import lach_01298.qmd.particle.ParticleStack;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.nred.nuclearcraft.helpers.Location.ncLoc;

public class QMDRecipeBuilder<T extends QMDRecipe> extends SimpleRecipeBuilder {
    private final T recipe;

    public QMDRecipeBuilder(T recipe) {
        super((ItemStack) ((Optional) recipe.getItemProducts().stream().findFirst().map((i) -> Arrays.stream(i.getItems()).findFirst()).orElse(Optional.of(ItemStack.EMPTY))).orElse(ItemStack.EMPTY));
        this.recipe = recipe;
    }

    protected QMDRecipeBuilder(T recipe, ItemStack result) {
        super(result);
        this.recipe = recipe;
    }

    public void save(RecipeOutput recipeOutput) {
        this.save(recipeOutput, this.getDefaultRecipeId());
    }

    public void save(RecipeOutput recipeOutput, String append) {
        this.save(recipeOutput, this.getDefaultRecipeId(append));
    }

    private ResourceLocation getDefaultRecipeId(String append) {
        if (!this.recipe.particleProducts.isEmpty()) {
            return getDefaultRecipeId(recipe.particleProducts, append);
        }
        return this.recipe.itemProducts.isEmpty() ? getDefaultRecipeId(this.recipe.fluidIngredients, this.recipe.fluidProducts, append) : RecipeBuilder.getDefaultRecipeId(this.getResult());
    }

    public static ResourceLocation getDefaultRecipeId(List<ParticleStack> outputs, String append) {
        return ncLoc((outputs.stream().map(e -> e.getParticle().getName()).reduce("", (string, particle) -> string + "_" + particle).replaceFirst("_", "") + append));
    }

    private ResourceLocation getDefaultRecipeId() {
        return this.getDefaultRecipeId("");
    }

    public void save(RecipeOutput output, ResourceLocation key) {
        key = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, key.withPrefix(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.recipe.getClass().getSimpleName() + "/").replace("_recipe", "")).getPath());
        Advancement.Builder advancement = output.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key)).rewards(AdvancementRewards.Builder.recipe(key)).requirements(AdvancementRequirements.Strategy.OR);
        output.accept(key, this.recipe, advancement.build(key.withPrefix("recipes/")));
    }
}
