/**
 * Demonstrates a NestedList, which uses a TreeStore to drill down through
 * hierarchical data
 */
Ext.define('Sencha.view.surveyData.NestedList', {
			requires : ['Sencha.model.Cars'],
			extend : 'Ext.Container',
			config : {
				layout : 'fit',
				items : [{
							xtype : 'nestedlist',
							store : "NestedListStore",
							displayField : 'text',
							listeners : {
								leafitemtap : function(me, list, index, item) {
									console.info(item);

									// var editorPanel =
									// Ext.getCmp('editorPanel') || new
									// Sencha.view.surveyData.EditorPanel();
									// editorPanel.setRecord(list.getStore().getAt(index));
									// if (!editorPanel.getParent()) {
									// Ext.Viewport.add(editorPanel);
									// }
									// editorPanel.show();
								}
							}
						}]
			}
		});
